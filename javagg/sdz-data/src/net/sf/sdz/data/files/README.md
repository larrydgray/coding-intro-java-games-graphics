# Reading and Writing Files with Java

Java has packages called `java.io` and `java.nio` which handle file I/O and stream I/O. There
are two basic types of files: binary files and text files — and a text file is technically a
binary file that contains at least ASCII (or, more commonly today, Unicode) text.

## Part 1: Plain Text Files

### Reading a Text File

`AFileReader` reads a text file line by line using a `BufferedReader`:

```java
try (BufferedReader br = new BufferedReader(new FileReader(aFileName))) {
    String aLine;
    while ((aLine = br.readLine()) != null) {
        System.out.println(aLine);
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

### Writing to a Text File

`AFileWriter` writes (overwriting) a text file using a `BufferedWriter`:

```java
try (BufferedWriter bw = new BufferedWriter(new FileWriter(aFileName))) {
    bw.write("The quick brown fox jumped over the fence.\n");
} catch (IOException e) {
    e.printStackTrace();
}
```

### Appending to a File

`AFileAppender` is the same as `AFileWriter`, but opens the file in append mode by passing
`true` as the second argument to `FileWriter`:

```java
try (BufferedWriter bw = new BufferedWriter(new FileWriter(aFileName, true))) {
    bw.write("The quick brown fox jumped over the fence.\n");
} catch (IOException e) {
    e.printStackTrace();
}
```

## Part 2: Delimited Files

Delimited files are files where data is separated by some symbol, such as pipe `|`, comma `,`,
or quoted-comma (CSV) `"a","b"`. Each line is usually a set of data fields, so the file ends up
looking much like a database table. Pipe (`|`) is used in the examples below since in multiple
rows it reads like a vertical line separating columns.

```
10/2/18|Coke Vending|1.75
10/3/18|Steak Meal|17.52
10/4/18|Gas for Van|54.13
```

Sample data matching what `TypedDelimitedFileReader`/`AFileReaderWriter`/`RecordReaderWriter`
actually parse (5 fields: date, note, amount, account, active) lives at
[`sdz-data/data/textfile1.txt`](../../../../../../../data/textfile1.txt) — copy it to your
working directory as `textfile1.txt` before running those.

### Parsing the Lines

`DelimitedFileReader` splits each line on `|` and prints the raw string fields:

```java
String[] dataRecord = aLine.split(Pattern.quote("|"));
for (int i = 0; i < dataRecord.length; i++) {
    System.out.println(dataRecord[i]);
}
```

### Parsing the Fields into Types

`TypedDelimitedFileReader` converts each field from a string into its proper type
(`Date`, `String`, `float`, `int`, `boolean`) and prints a formatted line:

```java
if (i == 0) date = new Date(dataRecord[i]);
if (i == 1) note = dataRecord[i];
if (i == 2) amount = Float.parseFloat(dataRecord[i]);
if (i == 3) account = Integer.parseInt(dataRecord[i]);
if (i == 4) active = Boolean.parseBoolean(dataRecord[i]);
```

### Reading and Writing Back Out

`AFileReaderWriter` reads the delimited file, reformats each record (note the date format
changes from `dd/MM/yy` to `MM/dd/yy`), and writes the result to a different file — proving
the round trip worked.

### Using a Record Object

`RecordReaderWriter` does the same round trip, but holds each record's fields in an
`ExpenseRecord` object (with a `toString()` that re-delimits the fields) instead of building
a formatted string inline. This is the natural next step toward a simple flat-file database:
add getters/setters, searching, and sorting, and you have something usable without JDBC —
though the data will still port easily into a real database later if the application outgrows
a flat file.

## Part 3: Property Files

A property file holds `name=value` pairs, one per line (Java's own `java.util.Properties`
does this too, but here it's hand-rolled to show how). The example file describes an RPG
character:

```
birth=2/2/2018
name=Larry the Rogue
level=10
wealth=15.25
magic=false
```

Copy this to your working directory as `properties1.txt` before running the examples below —
it lives at [`sdz-data/data/properties1.txt`](../../../../../../../data/properties1.txt).

`PropertiesFileReader` is the minimal version: split each line on `=` and print both parts.

`PropertiesReader` parses each line into a `PropertyRecord` (name/value pair) with typed
getters/setters (`getInt`, `getFloat`, `getBoolean`, `getDate` and their setters, which convert
to/from the record's raw `String value`), then demonstrates reading, displaying, mutating, and
re-displaying a character's stats via `findProperty` and `displayCharacterStats`.

> Note: the original article evolved this same class through a couple of earlier, buggier
> iterations (a broken `setFloat(int)`, a `setDate` that never stored the value) before landing
> here — those intermediate versions weren't ported since this final version supersedes them.

`PropertiesReaderWriter` is the same idea, but after mutating the character's stats it writes
the updated properties back out to `newproperties.txt`.

## Part 4: Fixed-Width / Row-Col "Spreadsheet" Files

A pipe-delimited text file can also be treated like a single spreadsheet sheet: access data by
row and column instead of just reading line by line. `SheetReader` is the minimal version —
split each line on `|` and print it out.

`SheetReaderWriter` wraps a `String[][]` in a `Sheet` object exposing `get`/`set` by `(row, col)`,
with typed accessors (`getInt`/`setInt`, `getFloat`/`setFloat`, `getBoolean`/`setBoolean`,
`getDate`/`setDate`) built on top of `getData`/`setData`. Each column is padded with whitespace
to the width of its widest value (`manageColumnSpace`); writing a value wider than the current
column width expands every cell in that column (`expandColWidth`) so the file stays aligned.
After loading `survey.txt` (cave-survey data), the constructor demonstrates mutating a handful
of cells and writes the result to `survey2.txt`. The sample input — with column widths already
aligned the way `manageColumnSpace` expects — lives at
[`sdz-data/data/survey.txt`](../../../../../../../data/survey.txt); copy it to your working
directory before running.

> As with the property-file article, this one iterates through a couple of untested
> intermediate versions of `SheetReader`/`SheetReaderWriter` before arriving at the tested
> version above — only the final, demonstrated-working version was ported.

## Part 5: Serialized Objects

To write objects to a file: wrap a `FileOutputStream` in an `ObjectOutputStream` and call
`writeObject(...)`. To read them back: wrap a `FileInputStream` in an `ObjectInputStream` and
call `readObject()`, casting the result back to the expected type. The object's class must
implement `Serializable`.

`ObjectWriterReader` writes a list of `Expense` objects (amount, date, category, note) to
`expenses.dat`, then reads them back and prints them. A couple of things worth knowing if you
run this yourself:

- The file is opened in **append** mode (`new FileOutputStream("expenses.dat", true)`), so
  running the example more than once appends a second serialization stream header to the same
  file, which will corrupt the read-back on the next run — delete `expenses.dat` between runs.
- Reading loops on `readObject()` until it throws (there's no explicit EOF check), matching how
  the original article left it.
- Custom serializable classes should define an explicit `serialVersionUID` (generated here via
  the JDK's `serialver` tool) so serialized data stays compatible across recompiles.

## Part 6: XML Data Files

Reading XML with the DOM API: load the whole file into a `Document`, then walk it with
`getElementsByTagName`, `getChildNodes`, `getAttributes`, and `getTextContent`. `name=value`
pairs on an element are its **attributes**; `<tag>text</tag>` content is retrieved via
`getTextContent()`.

`XMLReader` is a small text-adventure engine: it parses `adventure.xml` (a set of `<room>`
elements, each with a `description`, a `brief` summary, and `<door dir="..." room="..."/>`
exits) into `Room` objects, then runs a console game loop reading commands (`n`/`s`/`e`/`w`,
`enter`, `l`/`r`, `look`, `examine ...`, `get ...`, `debug`, `help`, `quit`) via `Scanner`.
New rooms can be added to `adventure.xml` without recompiling. The sample world data lives at
[`sdz-data/data/adventure.xml`](../../../../../../../data/adventure.xml) — run `XMLReader`
with the working directory set to `sdz-data/data/` (or copy the file next to wherever you run
it from) since it's loaded via a relative path.

> The original article's code had a stray broken `import` line and, more subtly, its
> `ArrayList<Room>`/`Iterator<Room>` generics got silently mangled by the WordPress HTML export
> into `<room>` (and leaked stray `</room>` closing tags after the code block) — both fixed
> here. A handful of multi-line string literals that had newlines embedded directly in the
> source rather than as `\n` escapes were also reassembled.

## Part 7: XML Object Serialization (XMLEncoder/XMLDecoder)

A second, different way to serialize objects to XML — `java.beans.XMLEncoder`/`XMLDecoder`,
JavaBeans-style rather than `java.io.Serializable`-based (contrast with
[Part 5](#part-5-serialized-objects) and the DOM-based reading/writing in
[Part 6](#part-6-xml-data-files)). `XMLEncoder` walks an object purely through its getters, and
`XMLDecoder` rebuilds it purely through its setters — no `Serializable` marker interface or
`serialVersionUID` needed, but the class **does** need a proper getter/setter for every field to
round-trip, which is why `JournalEntry`/`Journal` are real beans rather than plain data classes:

```java
XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("journal.xml")));
encoder.writeObject(aJournal);
encoder.close();
...
XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("journal.xml")));
Journal journal = (Journal) decoder.readObject();
```

`SerializeToXML` builds a `Journal` of four `JournalEntry` accounting records (date, credit/debit
notes and accounts, amount) and writes it to `journal.xml`. `DeserializeFromXML` reads it back
and prints it via `Journal.toString()`. The article notes it took some trial and error to get
right — an early attempt using inner classes and public fields (no getters/setters) silently
produced an empty XML file; only once `JournalEntry` became a proper top-level bean with real
accessors did the round trip work. That's the version ported here.

> The article also showed two non-functional "pseudo code" snippets (a `SerializeToXML`/
> `DeserializeFromXML` pair referencing an undefined `AnObject` class, explicitly labeled
> "does not run") just to illustrate the encoder/decoder API shape — those weren't ported since
> they don't compile as-is; the real, working example above supersedes them.

## Part 8: Random Access Files

`RandomAccessFile` (RAF) lets you `seek()` to any byte offset and read/write primitives or
Strings directly — useful for database-like access patterns (e.g. large tile-based game maps)
where you want to update one record without reading or rewriting the whole file. Primitives are
stored in their raw binary form (e.g. an `int` is always 4 bytes), which is what makes offset
math like `loc * 4` work.

Six examples, in increasing complexity:

1. `ReadWriteArrayRandomAccessFile` — writes/reads a flat `int[100]`.
2. `ReadWriteArrayRandomAccessFile2` — same, with a 2D `int[25][25]`.
3. `ReadWriteArrayRandomAccessFile3` — same, with a 3D `int[10][10][10]`.
4. `ReadWriteArrayRandomAccessFile4` — writes an `int[100]`, then reads a single value by
   index, multiplies it by 5, writes it back at the same offset, and re-reads it to confirm
   the update — the core "database-like" operation.
5. `ReadWriteArrayRandomAccessFile5` — same read/modify/write pattern, addressed by `(x, y)`
   into a 2D array (`print3x3` dumps the neighborhood around a point).
6. `ReadWriteArrayRandomAccessFile6` — same, addressed by `(layer, x, y)` into a 3D array
   (`print3x3x3` dumps a 3×3×3 neighborhood) — directly applicable to multi-layer tile maps
   (terrain layer, feature layer, etc).

---
*Ported from the "Local Data Storage" article series, softwaredeveloperzone.com.*
