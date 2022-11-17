package si.fri.rso.samples.imagecatalog.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.samples.imagecatalog.lib.Product;
import si.fri.rso.samples.imagecatalog.services.beans.ProductBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;



@ApplicationScoped
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    private Logger log = Logger.getLogger(ProductResource.class.getName());

    @Inject
    private ProductBean productBean;

    @Operation(description = "Get data for all products.", summary = "Get all products")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of products",
                    content = @Content(schema = @Schema(implementation = Product.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getProduct() {

        List<Product> productMetadata = productBean.get();

        return Response.status(Response.Status.OK).entity(productMetadata).build();
    }


    @Operation(description = "Get data for a product.", summary = "Product data")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Product data",
                    content = @Content(
                            schema = @Schema(implementation = Product.class))
            )})
    @GET
    @Path("/{productId}")
    public Response getProduct(@Parameter(description = "Product ID.", required = true)
                                     @PathParam("productId") Integer productId) {

        Product product = productBean.getProduct(productId);

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(product).build();
    }

    @Operation(description = "Add product data.", summary = "Add product")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Product data successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error.")
    })
    @POST
    public Response createProduct(@RequestBody(
            description = "DTO object with product data.",
            required = true, content = @Content(
            schema = @Schema(implementation = Product.class))) Product product) {

        if ((product.getTitle() == null || product.getBrand() == null || product.getPrice() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            product = productBean.createProduct(product);
        }

        return Response.status(Response.Status.CONFLICT).entity(product).build();

    }


    @Operation(description = "Update data for a product.", summary = "Update product")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Product data successfully updated."
            )
    })
    @PUT
    @Path("{productId}")
    public Response putImageMetadata(@Parameter(description = "Product data ID.", required = true)
                                     @PathParam("productId") Integer productId,
                                     @RequestBody(
                                             description = "DTO object with product data.",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = Product.class)))
                                     Product product){

        product = productBean.putImageMetadata(productId, product);

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @Operation(description = "Delete data for a product.", summary = "Delete product")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Product data successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("{productId}")
    public Response deleteImageMetadata(@Parameter(description = "Product ID.", required = true)
                                        @PathParam("productId") Integer imageMetadataId){

        boolean deleted = productBean.deleteProduct(imageMetadataId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }





}
