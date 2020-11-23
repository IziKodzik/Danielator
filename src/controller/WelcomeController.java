package controller;

import def.RobotService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;

public class WelcomeController implements Initializable {

	RobotService robot;
	List<Point> points = new ArrayList<>();
	@FXML
	private Button OkBommer;

	@FXML
	private AnchorPane pane;

	@FXML
	private Button bLoad;

	@FXML
	private Button bFill;

	@FXML
	private Button bSnipe;

	List<TextField> textFields = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			this.robot = new RobotService();
		} catch (AWTException e) {
			e.printStackTrace();
		}
//		for(int op = 436 ; op <= 436 + (23 + 10 +23)*6; op+=23 + 10 + 23){
//			this.points.add(new Point(590,op));
//			if(op == 436 + (23 + 10 + 23) * 3 )
//				this.points.add(new Point(854,603));
//		}
//		this.points.add(new Point(1044,500));
//		this.points.add(new Point(1016,561));
//		this.points.add(new Point(1092,561));
//		this.points.add(new Point(854, 770));
//		this.points.add(new Point(990, 775));

		this.OkBommer.setOnAction(e -> this.updateData());
		this.bLoad.setOnAction(e->this.load());
		this.bFill.setOnAction(e->this.fill());
		this.bSnipe.setOnAction(e->{

			Pane root;
			try{
				root = new Pane();
				Stage stage = new Stage();
				root.setStyle("-fx-background-color:#00FF7F");
				FileWriter w = new FileWriter("src/res/coords");
				w.write("");

				stage.initStyle(StageStyle.UNDECORATED);
				stage.setScene(new Scene(root,4000,4000));
				stage.setOpacity(0.2);
				stage.setX(0);
				stage.setY(0);
				root.setOnMouseClicked(c->{

						BufferedWriter writer;
					try {
						writer = new BufferedWriter(new FileWriter
								(new File("src/res/coords"),true));


						writer.append(String.valueOf(c.getX())).append(" ").append(String.valueOf(c.getY()));
						writer.newLine();
						root.getChildren().add(new Circle(c.getX(),c.getY(),10));
						System.out.println(c.getX() + " " + c.getY());

						writer.close();
					} catch (IOException ex) {
						ex.printStackTrace();

					}

				});
				stage.show();


			} catch (IOException ex) {
				ex.printStackTrace();
			}

		});

		List<Node> kids = pane.getChildren();
		kids.forEach(e -> {
			if(e instanceof  TextField)
				textFields.add((TextField) e);
		});
//		this.load();
	}

	public void updateData(){

		try {
			PrintWriter writer = new PrintWriter(new FileWriter(new File("src/res/data.txt")));
			this.textFields.forEach( e -> writer.println(e.getText()));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fill(){

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/res/coords")));
			int i = 0;
			for(String line = reader.readLine() ; line != null ; line = reader.readLine()){


				String[] split = line.split(" ");
				if ( i < 10){
					this.robot.fillTextField((int) Double.parseDouble(split[0])
							, (int) Double.parseDouble(split[1]),
							textFields.get(i).getText());

				} else if (i < 12){

					this.robot.fillTextField((int) Double.parseDouble(split[0])
							, (int) Double.parseDouble(split[1]),
							textFields.get(i).getText());
					this.robot.keyClick((char)(KeyEvent.VK_ENTER));

				}else
				if(i == 12){
					this.robot.fillTextField((int) Double.parseDouble(split[0])
							, (int) Double.parseDouble(split[1]),"PL");
					this.robot.keyClick((char)(KeyEvent.VK_ENTER));
				} else if (i == 13){
					this.robot.mouseMove((int) Double.parseDouble(split[0])
							, (int) Double.parseDouble(split[1]));

					this.robot.leftClick();
				}


				++i;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void iterateData(BiConsumer<Integer,String> consumer){

		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/res/data.txt"));
			int i = 0;
			for(String line = reader.readLine();line != null ; line = reader.readLine()) {
				consumer.accept(i++,line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void load() {
		this.iterateData((i,s)->{
			this.textFields.get(i).setText(s);
		});
	}

}
