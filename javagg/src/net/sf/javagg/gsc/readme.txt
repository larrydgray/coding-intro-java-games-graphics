The main working classes right now in this is Glyph and GlyphFactory, Image Strip 
and TextScreenPanel, everything else I pulled from old source and we will
need to do a lot of work to.

This will be a rewrite on this module that was for a text 
screen component. It is supposed to make writing text in a grid 
of cells cols and rows using bit map fixed width fonts. 
Its also supposed to make some image work and graphics work 
simpler with the text screen itself. 

Now how will this component be different than the JPanel or JTextPane 
or JTextArea?  It will have a retro feel. It will
more closely resemble a text screen in function. It will
also be lighter weight. It will be customized for game and 
retro game and phone game coding maybe. 

I primarily wanted this component for the MUD, but there
may be other uses, such as tile screens for turn based games.

I've pulled some interfaces and classes from the old source that
will be useful. Though they are subject to change in the simplification
process. Much of the old source is not useful. Some of the implementation
is useful. 

I have turned the old .java files into .txt so that the implementation
remains there for review. Later I will remove the old packages and files.

I had to rebuild the repo module because of some kind of commit errors. 
I put all the old source in a sourceforge.zip file.