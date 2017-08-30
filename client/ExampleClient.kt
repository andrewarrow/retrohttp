package co.example.client

import co.example.model.*
import java.util.*

interface ExampleClient {

  @PUT("admin/example/update")
  fun updateExample(
    @Header("User-Id") userId: String,
    @Body body: UpdateExampleBody
  ): Single<Example>

  @GET("admin/example")
  fun getExample(
    @Header("User-Id") userId: String,
    @Query("example_id") id: String
  ): Maybe<Example>
}

data class UpdateExampleBody(
  @field:JsonProperty("example_id")
  val exampleId: UUID,

  @field:JsonProperty("quantity")
  val quantity: Int = 0
)
