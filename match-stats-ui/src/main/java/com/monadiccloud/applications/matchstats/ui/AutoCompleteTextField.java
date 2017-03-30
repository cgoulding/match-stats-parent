package com.monadiccloud.applications.matchstats.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Connor Goulding
 */
public class AutoCompleteTextField<T> extends TextField
{
    /**
     * The popup used to select an entry.
     */
    private ContextMenu entriesPopup;

    private T resolved;

    /**
     * Construct a new AutoCompleteTextField.
     */
    public AutoCompleteTextField(Function<String, Collection<T>> searchFunction, Function<T, String> mapFunction)
    {
        super();

        entriesPopup = new ContextMenu();

        textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if (getText().length() == 0)
                {
                    entriesPopup.hide();
                }
                else
                {
                    List<T> searchResult = searchFunction.apply(getText()).stream().collect(Collectors.toList());
                    if (searchResult.size() == 1)
                    {
                        resolved = searchResult.get(0);
                        setText(mapFunction.apply(resolved));
                        entriesPopup.hide();
                    }
                    else if (searchResult.size() > 1)
                    {
                        populatePopup(searchResult, mapFunction);
                        if (!entriesPopup.isShowing())
                        {
                            entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                        }
                    }
                    else
                    {
                        entriesPopup.hide();
                    }
                }
            }
        });

        focusedProperty().addListener((observableValue, aBoolean, aBoolean2) -> entriesPopup.hide());
    }

    public T getResolved()
    {
        return resolved;
    }

    /**
     * Populate the entry set with the given search results.  Display is limited to 10 entries, for performance.
     *
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<T> searchResult, Function<T, String> textFunction)
    {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        // If you'd like more entries, modify this line.
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++)
        {
            final T result = searchResult.get(i);
            final String displayString = textFunction.apply(result);

            Label entryLabel = new Label(displayString);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);

            item.setOnAction(actionEvent ->
            {
                setText(displayString);
                resolved = result;
                entriesPopup.hide();
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }
}
