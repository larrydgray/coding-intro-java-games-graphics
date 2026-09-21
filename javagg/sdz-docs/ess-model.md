# ESS Model — an Easy-to-Use UML Modeling Tool

[ESS Model](http://essmodel.sourceforge.net/) is a lightweight, easy-to-use modeling tool that
helps you understand your own source or someone else's. At a glance it gives you UML diagrams
for the classes in a given folder — showing fields and methods in a box model, with lines
connecting related classes in the same package (folder).

Workflow for using it in an article/blog post: run it, screenshot the diagram (`PrtSc`), paste
into an image editor, crop, save, then embed the image in your post.

**Usage:** put the `.exe` somewhere on your `PATH`, `cd` into the folder (package) you want to
diagram, then run:

```
essmodel *.java
```

## More about ESS Model

- **Navigating large diagrams:** an overview pane (top left) lets you drag to scroll around; the
  dark area shows what's currently in view.
- **Ungrouped classes:** classes not in a package show up under "default package" or "all
  classes"; there's also an "unknown package" bucket.
- **Relationship lines:** solid lines indicate `extends` (parentage), dashed lines indicate
  `implements`.
- **Copy to clipboard:** select one or multiple class boxes and copy them out — handy for
  cropping small images for articles.
- **Input files:** only Java source/class files, plus some Delphi files.
- **Hiding classes:** right-click to temporarily hide classes from view — useful for
  screenshotting or clipboard-copying just a subset.
- **Built-in documentation generator:** present, but not preferred by the author, who'd rather
  use `javadoc.exe` and embed ESS Model's diagrams into the generated Javadoc instead.
- **Saving:** the whole diagram can be saved as PNG or WMF.

## Limitations

It's a genuinely good, lightweight class-UML visualization tool — but it only handles **one
package at a time**. If your classes subclass across different packages within the same
project, ESS Model can't show those cross-package relationships; it only shows relationships
within the current package, or against standard Java API classes being extended/implemented.

## The author's rating

A simplicity/functionality/versatility rating table from the original article, roughly: strong
for basic/simple use cases, weaker on advanced/versatile needs — overall summarized as "not
weak, needs more work to be a great tool, but strong enough to be very useful."

| | Functional | Versatile | Simple | Row Total |
|---|---|---|---|---|
| Advanced | -3 | 0 | 1 | -0.66 |
| Intermediate | 0 | 0 | 2 | -0.66 |
| Basic | 3 | 1 | 3 | 2.33 |
| **Column Total** | **0** | **0.33** | **2** | **0.77** |

---
*Ported from softwaredeveloperzone.com. This is a tooling review, not a code article — no
source code accompanies it.*
