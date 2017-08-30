package co.example.resource

import co.example.client.*
import io.dropwizard.jersey.params.UUIDParam
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject
import javax.validation.constraints.NotNull
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import java.util.UUID

@Path("/example")
@Produces(MediaType.TEXT_HTML)
class ExampleResource @Inject constructor(
  private val client: ExampleClient
) {

  @POST
  @Path("edit/{example_id}")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  fun editExample(
    @CookieParam("User-Data") userData: String,
    @PathParam("example_id") dropId: String,
    @FormParam("quantity") quantity: Int
  ): Single<Response> {
    return client
      .updateExample(
        userId = AppUser.getUserId(userData),
        body = UpdateExampleBody(exampleId, quantity))
      .map { _ -> Response.seeOther(URI("/example")).build() }
  }

  @GET
  @Path("edit/{id}")
  fun getEditExample(
    @CookieParam("User-Data") userData: String,
    @PathParam("id") id: String
  ): Maybe<DropEditView> {
    return client
      .getExample(AppUser.getUserId(userData), id)
      .map { example -> DropEditView(ExampleViewModel(example)) }
  }

}
