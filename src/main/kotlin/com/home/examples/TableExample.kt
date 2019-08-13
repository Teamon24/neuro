package com.home.examples

import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY
import javafx.scene.layout.Priority
import tornadofx.*
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KMutableProperty1 as KMutableProperty11

/**
 *
 */
data class Item(val sku : String, val descr : String, val price : Float, val taxable : Boolean)

class TableSelectView : View("TableSelectApp") {

    private val items = FXCollections.observableArrayList<Item>(
        Item("KBD-0455892", "Mechanical Keyboard", 100.0f, true),
        Item("145256", "Product Docs", 0.0f, false),
        Item("OR-198975", "O-Ring (100)", 10.0f, true)
    )

    var tblItems : TableView<Item> by singleAssign()
    var btnInventory : Button by singleAssign()
    var btnCalcTax : Button by singleAssign()

    override val root = vbox {
        tblItems = tableview(items) {

            var skuColumn = TableColumn<Item, String>("SKU")

            addColumnInternal(skuColumn, 0)


            prefWidth = 667.0
            prefHeight = 376.0

            columnResizePolicy = CONSTRAINED_RESIZE_POLICY

            vboxConstraints {
                vGrow = Priority.ALWAYS
            }
        }
        hbox {
            btnInventory = button("Inventory")
            btnCalcTax = button("Tax")

            spacing = 8.0
        }

        padding = insets(10.0)
        spacing = 10.0
    }

    init {

        btnInventory.disableProperty().bind( tblItems.selectionModel.selectedItemProperty().isNull )

        btnCalcTax.disableProperty().bind(
            tblItems.selectionModel.selectedItemProperty().isNull().or(
                Bindings.select<Boolean>(
                    tblItems.selectionModel.selectedItemProperty(),
                    "taxable"
                ).isEqualTo( false )
            )
        )
    }
}

class TableSelectApp : App(TableSelectView::class)