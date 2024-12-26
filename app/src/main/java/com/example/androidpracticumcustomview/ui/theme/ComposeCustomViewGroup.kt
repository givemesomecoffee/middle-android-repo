package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */

@Composable
fun ComposeCustomViewGroup(
    modifier: Modifier = Modifier,
    first: @Composable () -> Unit,
    second: @Composable () -> Unit
) {
    val offsetYFirst = remember { Animatable(0f) }
    val offsetYSecond = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }
    val targetOffset = remember { mutableStateOf(Pair(0f, 0f)) }

    LaunchedEffect(targetOffset.value) {
        if (targetOffset.value.first != 0f) {
            launch {
                alpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(DoubleAnimationSpec.FADE_IN_DURATION)
                )
            }
            launch {
                offsetYFirst.animateTo(
                    targetValue = targetOffset.value.first,
                    animationSpec = tween(DoubleAnimationSpec.SLIDE_IN_DURATION)
                ).toString()
            }
            launch {
                offsetYSecond.animateTo(
                    targetValue = targetOffset.value.second,
                    animationSpec = tween(DoubleAnimationSpec.SLIDE_IN_DURATION)
                )
            }
        }
    }

    Layout(
        modifier = modifier.background(Color.LightGray),
        content = {
            Box(
                Modifier
                    .wrapContentSize()
                    .offset { IntOffset(0, offsetYFirst.value.toInt()) }
                    .graphicsLayer { this.alpha = alpha.value }
            ) {
                first()
            }
            Box(
                Modifier
                    .wrapContentSize()
                    .offset { IntOffset(0, offsetYSecond.value.toInt()) }
                    .graphicsLayer { this.alpha = alpha.value }
            ) {
                second()
            }
        }
    ) { measurables, constraints ->
        if (measurables.size > 2) {
            throw IllegalStateException()
        }
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        val result = layout(width, height) {
            placeables.forEachIndexed { index, placeable ->
                val position =
                    IntOffset((width - placeable.width) / 2, (height - placeable.height) / 2)
                placeable.place(position)
            }
        }
        var offsets = Pair(0f, 0f)
        placeables.forEachIndexed { index, placeable ->
            when (index) {
                0 -> offsets = offsets.copy(first = -((height / 2) - placeable.height).toFloat())
                1 -> offsets = offsets.copy(second = ((height / 2) - placeable.height).toFloat())
            }
        }
        targetOffset.value = offsets
        result
    }
}

