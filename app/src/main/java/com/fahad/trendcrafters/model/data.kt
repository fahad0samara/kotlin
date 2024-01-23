import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryList(
  val id: Int,
  val name: String,
  val products: List<Product>
)

@Serializable
data class Product(
  val description: String,
  val discount: Boolean,
  val discountPercentage: Int,
  val id: Int,
  val imageUrl: String,
  val inStock: Int,
  val name: String,
  val price: Double? = null,
  val rating: Double,
  val shippingInfo: ShippingInfo,
  val sizes: List<String>? = null,
  val tags: List<String>,



)

@Serializable
data class ShippingInfo(
  @SerialName("estimatedDeliveryDays")
  val estimatedDeliveryDays: Int,
  @SerialName("freeShippingThreshold")
  val freeShippingThreshold: Double,
  @SerialName("shippingCost")
  val shippingCost: Double
)
