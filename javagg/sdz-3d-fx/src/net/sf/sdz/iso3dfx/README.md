# Simple 3D Graphics using JavaFX

A JavaFX/FXML rewrite of [`sdz-3d-swing`](../../../../../../sdz-3d-swing)'s isometric surface
demo — same projection math and terrain generation, redrawn on a `Canvas`. Three files, since
a JavaFX FXML app splits into an `Application` entry point, a controller class, and the FXML
layout itself (analogous to how an Android app splits code and XML layout).

## Simple3DFX (entry point)

Extends `javafx.application.Application` and overrides `start(Stage)`, which `launch()` calls
from `main()`. It loads `simple3d.fxml` as the scene root (the FXML file is the "scene graph" —
a tree of UI components, conceptually similar to the HTML DOM), wraps it in a `Scene`, and shows
it on the `Stage` (JavaFX's analogue of Swing's `JFrame`).

```java
Parent root = FXMLLoader.load(getClass().getResource("simple3d.fxml"));
Scene scene = new Scene(root);
stage.setScene(scene);
stage.show();
```

`main()` wraps `launch(args)` in a try/catch — without it, FXML load failures surface only as
an opaque `InvocationTargetException`; catching and printing `e.getCause()` gets you the actual
`NullPointerException` or whatever really went wrong.

## Simple3dController

Loaded by the `FXMLLoader` per `fx:controller` in the FXML. Holds the same `Point2D`/`Point3D`/
`Surface3D`/`transform3D` logic as the Swing version's `Iso3D`/`IsoPanel`, plus:

- `@FXML`-annotated fields for each button, injected by the loader (`modeLeftBtn`, `leftBtn`,
  `zoomPlusBtn`, etc) and a `Canvas canvas3d`.
- `@FXML`-annotated handler methods (`doModeLeft`, `left`, `zoomPlus`, etc) wired to buttons via
  the FXML's `onAction="#methodName"` attributes.
- `initialize(URL, ResourceBundle)` (from `Initializable`, called after FXML injection) — this
  is where the `GraphicsContext` is grabbed and the first draw happens; the article notes some
  initialization code didn't work correctly when placed in the constructor instead.
- `setElevationColor(y1, y2)` — a small addition over the Swing version: colors each line
  segment by its average elevation (dark blue → blue → aquamarine → green → gray → white, low
  to high).

> **Canvas redraw gotcha, straight from the article:** `clearRect()`/`fillRect()` alone didn't
> erase previously drawn content when lines were drawn with `moveTo()`/`lineTo()`/`stroke()` —
> switching to `strokeLine()` instead fixed it. The likely explanation is that `moveTo`/`lineTo`
> build up a path object that clearing doesn't touch, whereas `strokeLine()` draws directly.
> This code uses `strokeLine()`; the old `moveTo`/`lineTo`/`stroke()` version is left commented
> out in `draw2DShapes()` for reference, matching the original source.
>
> Also as in the original: the FXML wires up a `2D Y+` button (`twoDYPlusBtn`/`twoDYPlus`) but
> there's no `2D Y-` counterpart — an asymmetry in the source article, not something introduced
> during porting.

## simple3d.fxml

Layout: `VBox` → (unused `MenuBar`, a `GridPane` of buttons, a `Canvas`). Buttons get an
`fx:id` (exposed as an `@FXML` field in the controller) and an `onAction="#handlerName"`
(matched to an `@FXML` method) — both set via SceneBuilder's Code tab rather than hand-written.
`fx:controller="net.sf.sdz.iso3dfx.Simple3dController"` on the root `VBox` is what tells the
loader which controller to instantiate (the original article used a `simple3dfx` package;
renamed here to match this project's `net.sf.sdz.*` convention).

SceneBuilder (from Gluon) is the recommended way to edit the FXML rather than hand-writing it;
it integrates with NetBeans so opening the `.fxml` file launches SceneBuilder directly and
saving writes back into the project.

---
*Ported from softwaredeveloperzone.com.*
