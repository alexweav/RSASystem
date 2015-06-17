# RSASystem
An RSA cryptosystem, in the form of a Java application.  
Allows the user to encrypt and decrypt a string of plaintext, a text file, or any other filetype (read as a binary file) using RSA encryption.
The program can produce new RSA keysets upon request, at a variable length ranging from 8 bit to 2048 bit.
Keysets are stored as certificates on the hard disk using a .bin binary file format.

A text interface and a GUI are provided and are controllable using the command line arguments.
The absence of an argument starts the program's default interface, which is (currently) the text interface, causing the program to execute in console.

Recognized arguments:

    text        Runs the program in console using a text based interface.

    window      Runs the program with a graphical interface.

    controller  Executes code in the file controller.java.  Provides an interface in which the user may write their own code.

As of now, the GUI is still under development.
