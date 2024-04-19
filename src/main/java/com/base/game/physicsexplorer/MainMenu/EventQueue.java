package com.base.game.physicsexplorer.MainMenu;

import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

public class EventQueue {
    private List<ActionEvent> events = new ArrayList<>();

    public void add(ActionEvent event) {
        events.add(event);
    }

    public ActionEvent remove() {
        if (events.isEmpty()) {
            return null;
        }
        return events.remove(0);
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }
}