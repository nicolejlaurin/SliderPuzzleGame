import javafx.animation.Timeline;
        import javafx.application.Application;
        import javafx.geometry.Insets;
        import javafx.geometry.Point2D;
        import javafx.geometry.Pos;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextField;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.input.MouseEvent;
        import javafx.stage.Stage;
        import javafx.scene.layout.Pane;
        import javafx.scene.control.ListView;
        import javafx.collections.FXCollections;
        import javafx.event.*;
        import javafx.event.EventHandler;

        import java.awt.*;
        import java.util.*;
        import javafx.animation.KeyFrame;
        import javafx.util.Duration;


public class SliderPuzzleGameMain extends Application {
    Button[][] buttons;   // This will store all the Buttons;
    TextField time;
    Timeline updateTimer;
    int timer;
    boolean playingGame = false;
    Image[][] image;
    Point2D[][] points = null;
    Point2D[][] SolvedPuzzle;
    String name;
    Image BLANK;
    Button blankbutton;
    int randrow;
    int randcol;
    Point2D blankB;
    Image PetsTile;
    Image SceneryTile;
    Image LegoTile;
    Image NumbersTile;
    ListView<String> puzzleList = new ListView<>();
    Button Start = new Button("Start");
    Label thumbnail = new Label();


    public void start(Stage primaryStage) {

        Pane aPane = new Pane();

        //-----------------------------------------------------------------------------------------------------------
        //create buttons for each image

        buttons = new Button[4][4];
        image = new Image[4][4];
        points = new Point2D[4][4];
        SolvedPuzzle = new Point2D[4][4];



        BLANK = new Image(getClass().getResourceAsStream("BLANK.png"));

        PetsTile = new Image(getClass().getResourceAsStream("Pets_" + randrow + "" + randcol + ".png"));
        SceneryTile = new Image(getClass().getResourceAsStream("Scenery_" + randrow + "" + randcol + ".png"));
        LegoTile = new Image(getClass().getResourceAsStream("Lego_" + randrow + "" + randcol + ".png"));
        NumbersTile = new Image(getClass().getResourceAsStream("Numbers_" + randrow + "" + randcol + ".png"));

        name = puzzleList.getSelectionModel().getSelectedItem();

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                buttons[row][col] = new Button();
                points[row][col] = new Point2D(row, col);
                buttons[row][col].relocate(10 + col * 188, 10 + row * 188);
                buttons[row][col].setPrefSize(187, 187);
                buttons[row][col].setPadding(new Insets(0, 0, 0, 0));
                buttons[row][col].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("BLANK.png"))));

                SolvedPuzzle[row][col] = new Point2D(row, col);
                points[row][col] = new Point2D(row, col);

                aPane.getChildren().add(buttons[row][col]);

            }
        }


        //-----------------------------------------------------------------------------------------------------------------------------
        //attaching pictures to buttons and displaying thumbnail
        String[] Puzzles = {"Pets", "Scenery", "Lego", "Numbers"};
        puzzleList.setItems(FXCollections.observableArrayList(Puzzles));
        puzzleList.relocate(800, 210);
        puzzleList.setPrefSize(187, 150);


        thumbnail.relocate(800, 10);
        thumbnail.setPrefSize(187, 187);
        thumbnail.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("BLANK.png"))));


        puzzleList.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (puzzleList.getSelectionModel().getSelectedIndex() == 0) {
                    thumbnail.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Pets_Thumbnail.png"))));

                } else if (puzzleList.getSelectionModel().getSelectedIndex() == 1) {
                    thumbnail.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Scenery_Thumbnail.png"))));

                } else if (puzzleList.getSelectionModel().getSelectedIndex() == 2) {
                    thumbnail.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Lego_Thumbnail.png"))));


                } else if (puzzleList.getSelectionModel().getSelectedIndex() == 3) {
                    thumbnail.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Numbers_Thumbnail.png"))));


                }
            }
        });


        //-----------------------------------------------------------------------------------------------------------------------------
        // timer
        Start.relocate(800, 375);
        Start.setPrefSize(187, 30);
        Start.setAlignment(Pos.CENTER);
        Start.setStyle("-fx-background-color: green; " + "-fx-border-color: green; " + "-fx-padding: 4 4;" + "-fx-base: rgb(170,0,0)");


        Label timeLabel = new Label("Time");
        timeLabel.setPrefSize(50, 20);
        timeLabel.relocate(800, 420);
        time = new TextField("0:00");
        time.setPrefSize(100, 20);
        time.relocate(880, 420);

        timer = 0;


        updateTimer = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                timer++;
                time.setText(String.format("%02d",(timer / 60)) + ":" + String.format("%02d", (timer % 60)));

            }
        }));
        updateTimer.setCycleCount(Timeline.INDEFINITE);

        //-----------------------------------------------------------------------------------------------------------------------------
        //start, stop

        Start.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                updateTimer.play();


                if (playingGame) {
                    playingGame = false;
                    time.setText("0:00");
                    updateTimer.stop();
                    Start.setStyle("-fx-background-color: green; " + "-fx-border-color: green; " + "-fx-padding: 4 4;" + "-fx-base: rgb(170,0,0)");
                    Start.setText("Start");
                    thumbnail.setDisable(false);
                    puzzleList.setDisable(false);


                    for (int row = 0; row < 4; row++) {
                        for (int col = 0; col < 4; col++) {
                            buttons[row][col].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("BLANK.png"))));


                        }
                    }
                } else {
                    playingGame = true;
                    timer = 0;
                    Start.setStyle("-fx-background-color: red; " + "-fx-border-color: red; " + "-fx-padding: 4 4;" + "-fx-base: rgb(170,0,0)");
                    Start.setText("Stop");
                    thumbnail.setDisable(true);
                    puzzleList.setDisable(true);


                    for (int row = 0; row < 4; row++) {
                        for (int col = 0; col < 4; col++) {

                            final int r1 = row;
                            final int c1 = col;


                            if (puzzleList.getSelectionModel().getSelectedIndex() == 0) {


                                buttons[row][col].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Pets_" + row + "" + col + ".png"))));
                                image[row][col] = new Image(getClass().getResourceAsStream("Pets_" + row + "" + col + ".png"));

                            } else if (puzzleList.getSelectionModel().getSelectedIndex() == 1) {

                                buttons[row][col].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Scenery_" + row + "" + col + ".png"))));
                                image[row][col] = new Image(getClass().getResourceAsStream("Scenery_" + row + "" + col + ".png"));

                            } else if (puzzleList.getSelectionModel().getSelectedIndex() == 2) {
                                buttons[row][col].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Lego_" + row + "" + col + ".png"))));
                                image[row][col] = new Image(getClass().getResourceAsStream("Lego_" + row + "" + col + ".png"));

                            } else if (puzzleList.getSelectionModel().getSelectedIndex() == 3) {
                                buttons[row][col].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Numbers_" + row + "" + col + ".png"))));
                                image[row][col] = new Image(getClass().getResourceAsStream("Numbers_" + row + "" + col + ".png"));


                            }

                            //swapping during play time
                            buttons[row][col].setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    swap(r1, c1);
                                    checkWin();
                                    update();

                                }

                            });

                        }
                    }

                    shuffle();


                }

            }


        });


        //-----------------------------------------------------------------------------------------------------------------------------


        aPane.getChildren().addAll(puzzleList, thumbnail, Start, time, timeLabel);


        //-----------------------------------------------------------------------------------------------------------------------------


        aPane.setStyle("-fx-background-color: white;");
        aPane.setPadding(new Insets(10, 10, 10, 10));
        primaryStage.setTitle("Slider Puzzle Game");
        primaryStage.setScene(new Scene(aPane));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    //-----------------------------------------------------------------------------------------------------------------------------
    //shuffling and swap methods

    public void shuffle() {
        randcol = (int) (Math.random() * 4);
        randrow = (int) (Math.random() * 4);

        buttons[randrow][randcol].setGraphic(new ImageView(new Image(getClass().getResourceAsStream("BLANK.png"))));
        image[randrow][randcol] = new Image(getClass().getResourceAsStream("BLANK.png"));
        BLANK = image[randrow][randcol];
        blankbutton = buttons[randrow][randcol];

        blankB = points[randrow][randcol];


        for (int i = 0; i < 50; i++) {
            swap((int) (Math.random() * 4), (int) (Math.random() * 4));
        }

    }

    public void swap(int row, int col) {


        if (row < 3 && image[row + 1][col] == BLANK) {

            image[row + 1][col] = image[row][col];
            image[row][col] = BLANK;
            buttons[row + 1][col].setGraphic(new ImageView(image[row][col]));
            buttons[row][col].setGraphic(new ImageView(BLANK));
            points[row + 1][col] = points[row][col];
            blankB = new Point2D(row, col);
            blankB = SolvedPuzzle[row][col];
            points[row][col] = blankB;


        } else if (row > 0 && image[row - 1][col] == BLANK) {


            image[row - 1][col] = image[row][col];
            image[row][col] = BLANK;
            points[row - 1][col] = points[row][col];
            blankB = new Point2D(row, col);
            buttons[row - 1][col].setGraphic(new ImageView(image[row][col]));
            buttons[row][col].setGraphic(new ImageView(BLANK));
            blankB = SolvedPuzzle[row][col];
            points[row][col] = blankB;



        } else if (col < 3 && image[row][col + 1] == BLANK) {
            image[row][col + 1] = image[row][col];
            image[row][col] = BLANK;
            points[row][col + 1] = points[row][col];
            blankB = new Point2D(row, col);
            buttons[row][col + 1].setGraphic(new ImageView(image[row][col]));
            buttons[row][col].setGraphic(new ImageView(BLANK));
            blankB = SolvedPuzzle[row][col];
            points[row][col] = blankB;


        } else if (col > 0 && image[row][col - 1] == BLANK) {
            image[row][col - 1] = image[row][col];
            image[row][col] = BLANK;
            points[row][col - 1] = points[row][col];
            blankB = new Point2D(row, col);
            buttons[row][col - 1].setGraphic(new ImageView(image[row][col]));
            buttons[row][col].setGraphic(new ImageView(BLANK));
            blankB = SolvedPuzzle[row][col];
            points[row][col] = blankB;

        }

        update();

    }


    public void update() {

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                buttons[row][col].setGraphic(new ImageView(image[row][col]));
                buttons[row][col].setDisable(false);

            }
        }
    }

    public void checkWin() {

        if (Arrays.deepEquals(points, SolvedPuzzle)) {
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {

                    playingGame = false;
                    updateTimer.stop();
                    Start.setStyle("-fx-background-color: green; " + "-fx-border-color: green; " + "-fx-padding: 4 4;" + "-fx-base: rgb(170,0,0)");
                    Start.setText("Start");
                    thumbnail.setDisable(false);



                    if (puzzleList.getSelectionModel().getSelectedIndex() == 0) {
                        image[(int)blankB.getX()][(int)blankB.getY()] = new Image(getClass().getResourceAsStream("Pets_" + ((int)blankB.getX()) + "" + ((int)blankB.getY()) + ".png"));


                    } else if (puzzleList.getSelectionModel().getSelectedIndex() == 1) {
                        image[(int)blankB.getX()][(int)blankB.getY()] = new Image(getClass().getResourceAsStream("Scenery_" + ((int)blankB.getX()) + "" + ((int)blankB.getY()) + ".png"));


                    } else if (puzzleList.getSelectionModel().getSelectedIndex() == 2) {
                        image[(int)blankB.getX()][(int)blankB.getY()] = new Image(getClass().getResourceAsStream("Lego_" + ((int)blankB.getX()) + "" + ((int)blankB.getY()) + ".png"));


                    } else if (puzzleList.getSelectionModel().getSelectedIndex() == 3) {
                        image[(int)blankB.getX()][(int)blankB.getY()] = new Image(getClass().getResourceAsStream("Numbers_" + ((int)blankB.getX()) + "" + ((int)blankB.getY()) + ".png"));



                    }


                }
            }

            System.out.println("Game won");
        }

    }





    public static void main(String[] args) {
        launch(args);

    }

}
