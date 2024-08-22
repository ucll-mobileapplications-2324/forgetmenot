@file:OptIn(org.jetbrains.compose.resources.InternalResourceApi::class)

package forgetmenot.composeapp.generated.resources

import kotlin.OptIn
import org.jetbrains.compose.resources.DrawableResource

private object CommonMainDrawable0 {
  public val add: DrawableResource by 
      lazy { init_add() }

  public val compose_multiplatform: DrawableResource by 
      lazy { init_compose_multiplatform() }

  public val delete: DrawableResource by 
      lazy { init_delete() }

  public val edit: DrawableResource by 
      lazy { init_edit() }

  public val star: DrawableResource by 
      lazy { init_star() }
}

internal val Res.drawable.add: DrawableResource
  get() = CommonMainDrawable0.add

private fun init_add(): DrawableResource = org.jetbrains.compose.resources.DrawableResource(
  "drawable:add",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/forgetmenot.composeapp.generated.resources/drawable/add.xml", -1, -1),
    )
)

internal val Res.drawable.compose_multiplatform: DrawableResource
  get() = CommonMainDrawable0.compose_multiplatform

private fun init_compose_multiplatform(): DrawableResource =
    org.jetbrains.compose.resources.DrawableResource(
  "drawable:compose_multiplatform",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/forgetmenot.composeapp.generated.resources/drawable/compose-multiplatform.xml", -1, -1),
    )
)

internal val Res.drawable.delete: DrawableResource
  get() = CommonMainDrawable0.delete

private fun init_delete(): DrawableResource = org.jetbrains.compose.resources.DrawableResource(
  "drawable:delete",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/forgetmenot.composeapp.generated.resources/drawable/delete.xml", -1, -1),
    )
)

internal val Res.drawable.edit: DrawableResource
  get() = CommonMainDrawable0.edit

private fun init_edit(): DrawableResource = org.jetbrains.compose.resources.DrawableResource(
  "drawable:edit",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/forgetmenot.composeapp.generated.resources/drawable/edit.xml", -1, -1),
    )
)

internal val Res.drawable.star: DrawableResource
  get() = CommonMainDrawable0.star

private fun init_star(): DrawableResource = org.jetbrains.compose.resources.DrawableResource(
  "drawable:star",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/forgetmenot.composeapp.generated.resources/drawable/star.xml", -1, -1),
    )
)
