package in.varadharajan

import java.util
import java.util.concurrent.Executors

import org.eclipse.jetty.client.HttpClient
import org.eclipse.jetty.client.api.{ContentResponse, Result}
import org.eclipse.jetty.client.util.{BufferingResponseListener, StringContentProvider}
import org.eclipse.jetty.http.HttpStatus
import org.eclipse.jetty.http2.client.HTTP2Client
import org.eclipse.jetty.http2.client.http.HttpClientTransportOverHTTP2
import org.scalameter._

import scala.concurrent.duration.Duration
import scala.concurrent._

object HTTP2Test {
  val httpClient = {
    val client = new HttpClient(new HttpClientTransportOverHTTP2(new HTTP2Client), null)
    client.start()
    client.setMaxConnectionsPerDestination(100)
    client
  }

  val content =
    """
      |<some json>
    """.stripMargin

  val default: Array[Byte] = Array()

  val executorService = Executors.newFixedThreadPool(100)
  implicit val executionContext = ExecutionContext.fromExecutorService(executorService)

  def request() = {
    val promise: Promise[Array[Byte]] = Promise[Array[Byte]]
    httpClient
      .POST("http://url")
      .content(new StringContentProvider(content), "application/json")
      .send(new BufferingResponseListener(20971520) {
        override def onComplete(result: Result) = {
          if(result.isSucceeded && result.getResponse.getStatus == HttpStatus.OK_200) {
            val content: Array[Byte] = getContent
            promise.success(content)
          }
          else {
            println("Boola")
            promise.success(default)
          }
        }
      })

    promise.future.map(x => {
      Thread.sleep(100)
      x
    })
  }

  def main(args: Array[String]): Unit = {
    val time = measure {
      val futures = (0 to 80).map(_ => request())
      val f = Future.sequence(futures)
      println(Await.ready(f, Duration.Inf).value.get)
    }

    println(time)
  }
}
