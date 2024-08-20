package com.id.shuttershop.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.domain.product.ProductFilterParams
import com.id.domain.product.SortingFilter
import com.id.domain.utils.formatToString
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.PrimaryTextField
import com.id.shuttershop.ui.components.button.PrimaryButton
import com.id.shuttershop.ui.components.button.PrimaryTextButton
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andre.
 * Date: 12/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    modifier: Modifier = Modifier,
    filterParams: ProductFilterParams,
    onShowProduct: (ProductFilterParams) -> Unit,
    changeBottomSheetValue: (Boolean) -> Unit = {},
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var filterStatus by remember {
        mutableStateOf(filterParams)
    }
    val filterEvent = FilterSheetEvent(
        onSortFilterChange = {
            val status = if (filterStatus.sortBy == it) null else it
            filterStatus = filterStatus.copy(sortBy = status)
        },
        onHighestPriceChange = { filterStatus = filterStatus.copy(highestPrice = it) },
        onLowestPriceChange = { filterStatus = filterStatus.copy(lowestPrice = it) },
        onResetClick = { filterStatus = ProductFilterParams() }
    )

    ModalBottomSheet(
        modifier = modifier,
        sheetState = bottomSheetState,
        onDismissRequest = { changeBottomSheetValue(false) }) {
        FilterBottomSheetContent(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 10.dp),
            event = filterEvent,
            onShowProduct = {
                onShowProduct(filterStatus)
                changeBottomSheetValue(false)
            },
            filterStatus = filterStatus
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun FilterBottomSheetContent(
    modifier: Modifier = Modifier,
    event: FilterSheetEvent,
    filterStatus: ProductFilterParams,
    onShowProduct: () -> Unit,
) {
    Column(
        modifier = modifier.padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Filter",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            PrimaryTextButton(text = "Reset", onClick = event.onResetClick)
        }
        Text(text = stringResource(R.string.text_sort), style = MaterialTheme.typography.labelLarge)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SortingFilter.entries.forEach {
                FilterChip(
                    selected = filterStatus.sortBy == it.value,
                    onClick = { event.onSortFilterChange.invoke(it.value) },
                    label = { Text(text = it.title) })
            }
        }
        Text(
            text = stringResource(R.string.text_price),
            style = MaterialTheme.typography.labelLarge
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            PrimaryTextField(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.text_lowest),
                value = filterStatus.lowestPrice.formatToString() ?: "",
                onTextChange = { event.onLowestPriceChange(it.toDoubleOrNull() ?: 0.0) })
            PrimaryTextField(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.text_highest),
                value = filterStatus.highestPrice.formatToString() ?: "",
                onTextChange = { event.onHighestPriceChange.invoke(it.toDoubleOrNull() ?: 0.0) })
        }
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.text_show_product),
            onClick = onShowProduct
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun BottomFilterHomePreview() {
    ShutterShopTheme {
        FilterBottomSheetContent(
            event = FilterSheetEvent(
                onSortFilterChange = {},
                onHighestPriceChange = {},
                onLowestPriceChange = {},
                onResetClick = {}
            ),
            filterStatus = ProductFilterParams(),
            onShowProduct = {}
        )
    }
}