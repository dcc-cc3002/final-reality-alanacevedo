package com.github.alanacevedo.finalreality.gui.phaseScene;

import com.github.alanacevedo.finalreality.controller.GameController;
import com.github.alanacevedo.finalreality.controller.Settings;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class WaitingPhaseScene extends AbstractPhaseScene {
    private GameController controller;

    private Text phaseLabel = new Text();

    private Group root;


    public WaitingPhaseScene(GameController controller) {
        this.controller = controller;
        root = new Group();

        root.getChildren().add(phaseLabel);
        phaseLabel.setLayoutX(10);
        phaseLabel.setLayoutY(10);
        phaseLabel.setFill(Color.WHITE);


    }

    public void handleTimer() {

        phaseLabel.setText(controller.getPhase().getName() + "Phase");
    }

    public Group getRoot() {
        return root;
    }




}
