package com.company;


import com.company.calculations.MyVector;
import com.company.calculations.Point;
import com.sun.javafx.scene.paint.GradientUtils;
import exceptions.IllegalParameterException;
import exceptions.UnmatchedDimensionException;
import java.util.ArrayList;
import java.util.Collection;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ThreeDimensionSystem extends Application {

  public static ArrayList<MyVector> allVectors = new ArrayList<>();
  public static ArrayList<Point> allPoints = new ArrayList<>();

  final Group root = new Group();
  final Group axisGroup = new Group();
  final Group vectorGroup = new Group();
  final Group pointGroup = new Group();
  final Xform world = new Xform();
  final PerspectiveCamera camera = new PerspectiveCamera(true);
  final Xform cameraXform = new Xform();
  final Xform cameraXform2 = new Xform();
  final Xform cameraXform3 = new Xform();
  final double cameraDistance = 450;
  private Timeline timeline;
  boolean timelinePlaying = false;
  double ONE_FRAME = 1.0 / 24.0;
  double DELTA_MULTIPLIER = 200.0;
  double CONTROL_MULTIPLIER = 0.1;
  double SHIFT_MULTIPLIER = 0.1;
  double ALT_MULTIPLIER = 0.5;

  double mousePosX;
  double mousePosY;
  double mouseOldX;
  double mouseOldY;
  double mouseDeltaX;
  double mouseDeltaY;


  private void buildCamera() {
    root.getChildren().add(cameraXform);
    cameraXform.getChildren().add(cameraXform2);
    cameraXform2.getChildren().add(cameraXform3);
    cameraXform3.getChildren().add(camera);
    cameraXform3.setRotateZ(180.0);

    camera.setNearClip(0.1);
    camera.setFarClip(10000.0);
    camera.setTranslateZ(-cameraDistance);
    cameraXform.ry.setAngle(320.0);
    cameraXform.rx.setAngle(40);
  }

  private void buildPoints(){

      for(Point point : allPoints){
      Sphere spherePoint = new Sphere();
      spherePoint.setRadius(1);

        pointGroup.getChildren().addAll(spherePoint);
      }



    world.getChildren().addAll(pointGroup);

  }

  private void buildVectors() throws UnmatchedDimensionException, IllegalParameterException {
    Rotate rotation;
    int multiplier = 15;
    //TODO different Axis cases

    for (MyVector vector : allVectors) {
      MyVector zVector = new MyVector(vector.getX(), vector.getY(), 0);
      //Create rotation axis
      MyVector crossProduct = vector.crossProduct(zVector);
      Point3D roationAxis = new Point3D(crossProduct.getX(), crossProduct.getY(),
          crossProduct.getZ());
      System.out.println(vector);

      //Create Vector line
      Line vectorLine = new Line(vector.getStart().getX() * multiplier, vector.getStart().getY() * multiplier,
          vector.getEnd().getX() * multiplier, vector.getEnd().getY() * multiplier);
      vectorLine.setStroke(vector.getColor());
      vectorLine.setStrokeWidth(1);

      //Calulate rotation value for z axis
      double rotVal =
          (vector.getX() * zVector.getX() + vector.getY() * zVector.getY() + vector.getZ() * zVector.getZ())
              / (vector.getLength() * zVector.getLength());
      System.out.println("Rotation Value" + rotVal);
      double rotDegree = Math.toDegrees(Math.acos(rotVal));
      System.out.println(rotDegree);
      rotation = new Rotate(rotDegree);
      rotation.setAxis(roationAxis);
      if (rotDegree > 0 && vector.getZ() != 0) {
        vectorLine.getTransforms().add(rotation);
      }
      Text name = new Text(vector.getName());
      name.getTransforms().add(rotation);
      name.setX(vectorLine.endXProperty().getValue());
      name.setY(vectorLine.endYProperty().getValue());

      vectorGroup.getChildren().addAll(vectorLine, name);
    }

    world.getChildren().addAll(vectorGroup);
  }

  private void buildAxes() {
    System.out.println("buildAxes()");
    final PhongMaterial redMaterial = new PhongMaterial();
    redMaterial.setDiffuseColor(Color.DARKRED);
    redMaterial.setSpecularColor(Color.RED);

    final PhongMaterial greenMaterial = new PhongMaterial();
    greenMaterial.setDiffuseColor(Color.DARKGREEN);
    greenMaterial.setSpecularColor(Color.GREEN);

    final PhongMaterial blueMaterial = new PhongMaterial();
    blueMaterial.setDiffuseColor(Color.DARKBLUE);
    blueMaterial.setSpecularColor(Color.BLUE);

    final Box xAxis = new Box(240.0, 1, 1);
    final Box yAxis = new Box(1, 240.0, 1);
    final Box zAxis = new Box(1, 1, 240.0);

    Text xText = new Text("x");
    xText.setX(120);
    Text yText = new Text("y");
    yText.setY(120);
    yText.setRotate(180);
    Text zText = new Text("z");
    zText.setTranslateZ(120);

    xAxis.setMaterial(redMaterial);
    yAxis.setMaterial(greenMaterial);
    zAxis.setMaterial(blueMaterial);

    axisGroup.getChildren().addAll(xAxis, xText, yAxis, yText, zAxis, zText);
    world.getChildren().addAll(axisGroup);
  }

  private void buildScene() {
    System.out.println("buildScene");
    root.getChildren().add(world);
  }

  @Override
  public void start(Stage primaryStage)
      throws UnmatchedDimensionException, IllegalParameterException {
    System.out.println("start");
    buildScene();
    buildCamera();
    buildAxes();
    buildVectors();
    buildPoints();

    Scene scene = new Scene(root, 1024, 768, true);
    scene.setFill(Color.GREY);
    handleKeyboard(scene, world);
    handleMouse(scene, world);

    primaryStage.setTitle("Molecule Sample Application");
    primaryStage.setScene(scene);
    primaryStage.show();
    scene.setCamera(camera);
  }


  /**
   * The main() method is ignored in correctly deployed JavaFX application. main() serves only as
   * fallback in case the application can not be launched through deployment artifacts, e.g., in
   * IDEs with limited FX support. NetBeans ignores main().
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) throws UnmatchedDimensionException {
    System.setProperty("prism.dirtyopts", "false");
    MyVector v1 = new MyVector("AB",new Point(0,0,0), new Point(2,0,0));
    MyVector v2 = new MyVector("BC",new Point(2,0,0), new Point(1,1,0));
    MyVector v3 = new MyVector("CA",new Point(1,1,0), new Point(0,0,0));

    allVectors.add(v1);
    allVectors.add(v2);
    allVectors.add(v3);

    MyVector v4 = new MyVector(1,1,0);
    MyVector v5 = new MyVector(1,0,1);
    System.out.println(v4.dotProduct(v5) + " sdasd");



    Point p1 = new Point(1,2,3);
    allPoints.add(p1);


    launch(args);
  }

  //
// The handleCameraViews file contains the handleMouse() and handleKeyboard()
// methods that are used in the MoleculeSampleApp application to handle the
// different 3D camera views.  These methods are used in the Getting Started with
// JavaFX 3D Graphics tutorial.
//

  private void handleMouse(Scene scene, final Node root) {
    scene.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent me) {
        mousePosX = me.getSceneX();
        mousePosY = me.getSceneY();
        mouseOldX = me.getSceneX();
        mouseOldY = me.getSceneY();
      }
    });
    scene.setOnScroll(new EventHandler<ScrollEvent>() {
      @Override
      public void handle(ScrollEvent scrollEvent) {
        double z = camera.getTranslateZ();
        double newZ = z + mouseDeltaX + scrollEvent.getDeltaX() + scrollEvent.getDeltaY();
        camera.setTranslateZ(newZ);
      }
    });
    scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent me) {
        mouseOldX = mousePosX;
        mouseOldY = mousePosY;
        mousePosX = me.getSceneX();
        mousePosY = me.getSceneY();
        mouseDeltaX = (mousePosX - mouseOldX);
        mouseDeltaY = (mousePosY - mouseOldY);

        double modifier = 1.0;
        double modifierFactor = 0.1;

        if (me.isControlDown()) {
          modifier = 0.1;
        }
        if (me.isShiftDown()) {
          modifier = 15.0;
        }
        if (me.isPrimaryButtonDown()) {
          cameraXform.ry.setAngle(
              cameraXform.ry.getAngle() - mouseDeltaX * modifierFactor * modifier * 2.0);  // +
          cameraXform.rx.setAngle(
              cameraXform.rx.getAngle() + mouseDeltaY * modifierFactor * modifier * 2.0);  // -
        } else if (me.isSecondaryButtonDown()) {
          double z = camera.getTranslateZ();
          double newZ = z + mouseDeltaX * modifierFactor * modifier;
          camera.setTranslateZ(newZ);
        } else if (me.isMiddleButtonDown()) {
          cameraXform2.t
              .setX(cameraXform2.t.getX() + mouseDeltaX * modifierFactor * modifier * 0.3);  // -
          cameraXform2.t
              .setY(cameraXform2.t.getY() + mouseDeltaY * modifierFactor * modifier * 0.3);  // -
        }
      }
    });
  }

  private void handleKeyboard(Scene scene, final Node root) {
    final boolean moveCamera = true;
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        Duration currentTime;
        switch (event.getCode()) {
          case Z:
            if (event.isShiftDown()) {
              cameraXform.ry.setAngle(0.0);
              cameraXform.rx.setAngle(0.0);
              camera.setTranslateZ(-300.0);
            }
            cameraXform2.t.setX(0.0);
            cameraXform2.t.setY(0.0);
            break;
          case X:
            if (event.isControlDown()) {
              if (axisGroup.isVisible()) {
                System.out.println("setVisible(false)");
                axisGroup.setVisible(false);
              } else {
                System.out.println("setVisible(true)");
                axisGroup.setVisible(true);
              }
            }
            break;
          case SPACE:
            if (timelinePlaying) {
              timeline.pause();
              timelinePlaying = false;
            } else {
              timeline.play();
              timelinePlaying = true;
            }
            break;
          case UP:
            if (event.isControlDown() && event.isShiftDown()) {
              cameraXform2.t.setY(cameraXform2.t.getY() - 10.0 * CONTROL_MULTIPLIER);
            } else if (event.isAltDown() && event.isShiftDown()) {
              cameraXform.rx.setAngle(cameraXform.rx.getAngle() - 10.0 * ALT_MULTIPLIER);
            } else if (event.isControlDown()) {
              cameraXform2.t.setY(cameraXform2.t.getY() - 1.0 * CONTROL_MULTIPLIER);
            } else if (event.isAltDown()) {
              cameraXform.rx.setAngle(cameraXform.rx.getAngle() - 2.0 * ALT_MULTIPLIER);
            } else if (event.isShiftDown()) {
              double z = camera.getTranslateZ();
              double newZ = z + 5.0 * SHIFT_MULTIPLIER;
              camera.setTranslateZ(newZ);
            }
            break;
          case DOWN:
            if (event.isControlDown() && event.isShiftDown()) {
              cameraXform2.t.setY(cameraXform2.t.getY() + 10.0 * CONTROL_MULTIPLIER);
            } else if (event.isAltDown() && event.isShiftDown()) {
              cameraXform.rx.setAngle(cameraXform.rx.getAngle() + 10.0 * ALT_MULTIPLIER);
            } else if (event.isControlDown()) {
              cameraXform2.t.setY(cameraXform2.t.getY() + 1.0 * CONTROL_MULTIPLIER);
            } else if (event.isAltDown()) {
              cameraXform.rx.setAngle(cameraXform.rx.getAngle() + 2.0 * ALT_MULTIPLIER);
            } else if (event.isShiftDown()) {
              double z = camera.getTranslateZ();
              double newZ = z - 5.0 * SHIFT_MULTIPLIER;
              camera.setTranslateZ(newZ);
            }
            break;
          case RIGHT:
            if (event.isControlDown() && event.isShiftDown()) {
              cameraXform2.t.setX(cameraXform2.t.getX() + 10.0 * CONTROL_MULTIPLIER);
            } else if (event.isAltDown() && event.isShiftDown()) {
              cameraXform.ry.setAngle(cameraXform.ry.getAngle() - 10.0 * ALT_MULTIPLIER);
            } else if (event.isControlDown()) {
              cameraXform2.t.setX(cameraXform2.t.getX() + 1.0 * CONTROL_MULTIPLIER);
            } else if (event.isAltDown()) {
              cameraXform.ry.setAngle(cameraXform.ry.getAngle() - 2.0 * ALT_MULTIPLIER);
            }
            break;
          case LEFT:
            if (event.isControlDown() && event.isShiftDown()) {
              cameraXform2.t.setX(cameraXform2.t.getX() - 10.0 * CONTROL_MULTIPLIER);
            } else if (event.isAltDown() && event.isShiftDown()) {
              cameraXform.ry.setAngle(cameraXform.ry.getAngle() + 10.0 * ALT_MULTIPLIER);  // -
            } else if (event.isControlDown()) {
              cameraXform2.t.setX(cameraXform2.t.getX() - 1.0 * CONTROL_MULTIPLIER);
            } else if (event.isAltDown()) {
              cameraXform.ry.setAngle(cameraXform.ry.getAngle() + 2.0 * ALT_MULTIPLIER);  // -
            }
            break;
        }
      }
    });
  }

}

class Xform extends Group {

  public enum RotateOrder {
    XYZ, XZY, YXZ, YZX, ZXY, ZYX
  }

  public Translate t = new Translate();
  public Translate p = new Translate();
  public Translate ip = new Translate();
  public Rotate rx = new Rotate();

  {
    rx.setAxis(Rotate.X_AXIS);
  }

  public Rotate ry = new Rotate();

  {
    ry.setAxis(Rotate.Y_AXIS);
  }

  public Rotate rz = new Rotate();

  {
    rz.setAxis(Rotate.Z_AXIS);
  }

  public Scale s = new Scale();

  public Xform() {
    super();
    getTransforms().addAll(t, rz, ry, rx, s);
  }


  public Xform(RotateOrder rotateOrder) {
    super();
    // choose the order of rotations based on the rotateOrder
    switch (rotateOrder) {
      case XYZ:
        getTransforms().addAll(t, p, rz, ry, rx, s, ip);
        break;
      case XZY:
        getTransforms().addAll(t, p, ry, rz, rx, s, ip);
        break;
      case YXZ:
        getTransforms().addAll(t, p, rz, rx, ry, s, ip);
        break;
      case YZX:
        getTransforms().addAll(t, p, rx, rz, ry, s, ip);  // For Camera
        break;
      case ZXY:
        getTransforms().addAll(t, p, ry, rx, rz, s, ip);
        break;
      case ZYX:
        getTransforms().addAll(t, p, rx, ry, rz, s, ip);
        break;
    }

  }

  public void setTranslate(double x, double y, double z) {
    t.setX(x);
    t.setY(y);
    t.setZ(z);
  }

  public void setTranslate(double x, double y) {
    t.setX(x);
    t.setY(y);
  }

  // Cannot override these methods as they are final:
  // public void setTranslateX(double x) { t.setX(x); }
  // public void setTranslateY(double y) { t.setY(y); }
  // public void setTranslateZ(double z) { t.setZ(z); }
  // Use these methods instead:
  public void setTx(double x) {
    t.setX(x);
  }

  public void setTy(double y) {
    t.setY(y);
  }

  public void setTz(double z) {
    t.setZ(z);
  }

  public void setRotate(double x, double y, double z) {
    rx.setAngle(x);
    ry.setAngle(y);
    rz.setAngle(z);
  }

  public void setRotateX(double x) {
    rx.setAngle(x);
  }

  public void setRotateY(double y) {
    ry.setAngle(y);
  }

  public void setRotateZ(double z) {
    rz.setAngle(z);
  }

  public void setRx(double x) {
    rx.setAngle(x);
  }

  public void setRy(double y) {
    ry.setAngle(y);
  }

  public void setRz(double z) {
    rz.setAngle(z);
  }

  public void setScale(double scaleFactor) {
    s.setX(scaleFactor);
    s.setY(scaleFactor);
    s.setZ(scaleFactor);
  }

  public void setScale(double x, double y, double z) {
    s.setX(x);
    s.setY(y);
    s.setZ(z);
  }

  // Cannot override these methods as they are final:
  // public void setScaleX(double x) { s.setX(x); }
  // public void setScaleY(double y) { s.setY(y); }
  // public void setScaleZ(double z) { s.setZ(z); }
  // Use these methods instead:
  public void setSx(double x) {
    s.setX(x);
  }

  public void setSy(double y) {
    s.setY(y);
  }

  public void setSz(double z) {
    s.setZ(z);
  }

  public void setPivot(double x, double y, double z) {
    p.setX(x);
    p.setY(y);
    p.setZ(z);
    ip.setX(-x);
    ip.setY(-y);
    ip.setZ(-z);
  }

  public void reset() {
    t.setX(0.0);
    t.setY(0.0);
    t.setZ(0.0);
    rx.setAngle(0.0);
    ry.setAngle(0.0);
    rz.setAngle(0.0);
    s.setX(1.0);
    s.setY(1.0);
    s.setZ(1.0);
    p.setX(0.0);
    p.setY(0.0);
    p.setZ(0.0);
    ip.setX(0.0);
    ip.setY(0.0);
    ip.setZ(0.0);
  }

  public void resetTSP() {
    t.setX(0.0);
    t.setY(0.0);
    t.setZ(0.0);
    s.setX(1.0);
    s.setY(1.0);
    s.setZ(1.0);
    p.setX(0.0);
    p.setY(0.0);
    p.setZ(0.0);
    ip.setX(0.0);
    ip.setY(0.0);
    ip.setZ(0.0);
  }

  public void debug() {
    System.out.println("t = (" +
        t.getX() + ", " +
        t.getY() + ", " +
        t.getZ() + ")  " +
        "r = (" +
        rx.getAngle() + ", " +
        ry.getAngle() + ", " +
        rz.getAngle() + ")  " +
        "s = (" +
        s.getX() + ", " +
        s.getY() + ", " +
        s.getZ() + ")  " +
        "p = (" +
        p.getX() + ", " +
        p.getY() + ", " +
        p.getZ() + ")  " +
        "ip = (" +
        ip.getX() + ", " +
        ip.getY() + ", " +
        ip.getZ() + ")");
  }
}

