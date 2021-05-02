import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WordSearch implements ActionListener {
    static JTextArea searchAreaText = new JTextArea();
    static JScrollPane searchArea = new JScrollPane(searchAreaText);
    static JLabel outputLabel = new JLabel("Output:");
    static JTextArea outputText = new JTextArea();
    static JScrollPane outputArea = new JScrollPane(outputText);
    static JButton searchButton = new JButton("Search");
    static JFrame frame = new JFrame("Word Search");
    static JLabel rowNumberLabel = new JLabel("Number of Rows:");
    static JTextField rowNumberField = new JTextField(4);
    static JLabel rowCharacterLabel = new JLabel("Characters Per Row:");
    static JTextField rowCharacterField = new JTextField(4);
    static JLabel searchLabel = new JLabel("Word Search Text:");
    static JLabel wordLabel = new JLabel("Words (Separate Using New Line):");
    static JTextArea wordAreaText = new JTextArea();
    static JScrollPane wordArea = new JScrollPane(wordAreaText);

    public void actionPerformed(ActionEvent e) {
        search();
    }

    public static void main(String[] args) {
        new WordSearch().render();
    }

    void render() {
        wordArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        outputText.setEditable(false);
        Container contentPane = frame.getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);
        contentPane.add(rowNumberLabel);
        layout.putConstraint(SpringLayout.WEST, rowNumberLabel, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, rowNumberLabel, 5, SpringLayout.NORTH, contentPane);
        contentPane.add(rowNumberField);
        layout.putConstraint(SpringLayout.WEST, rowNumberField, 26, SpringLayout.EAST, rowNumberLabel);
        layout.putConstraint(SpringLayout.NORTH, rowNumberField, 5, SpringLayout.NORTH, contentPane);
        contentPane.add(rowCharacterLabel);
        layout.putConstraint(SpringLayout.WEST, rowCharacterLabel, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, rowCharacterLabel, 5, SpringLayout.SOUTH, rowNumberLabel);
        contentPane.add(rowCharacterField);
        layout.putConstraint(SpringLayout.WEST, rowCharacterField, 5, SpringLayout.EAST, rowCharacterLabel);
        layout.putConstraint(SpringLayout.NORTH, rowCharacterField, 0, SpringLayout.NORTH, rowCharacterLabel);
        contentPane.add(searchLabel);
        layout.putConstraint(SpringLayout.WEST, searchLabel, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, searchLabel, 5, SpringLayout.SOUTH, rowCharacterLabel);
        contentPane.add(searchArea);
        layout.putConstraint(SpringLayout.WEST, searchArea, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.EAST, searchArea, -365, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, searchArea, 5, SpringLayout.SOUTH, searchLabel);
        layout.putConstraint(SpringLayout.SOUTH, contentPane, 240, SpringLayout.SOUTH, searchArea);
        contentPane.add(outputLabel);
        layout.putConstraint(SpringLayout.WEST, outputLabel, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, outputLabel, -220, SpringLayout.SOUTH, contentPane);
        contentPane.add(outputArea);
        layout.putConstraint(SpringLayout.NORTH, outputArea, 5, SpringLayout.SOUTH, outputLabel);
        layout.putConstraint(SpringLayout.SOUTH, outputArea, -45, SpringLayout.SOUTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, outputArea, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.EAST, outputArea, -365, SpringLayout.EAST, contentPane);
        contentPane.add(searchButton);
        layout.putConstraint(SpringLayout.NORTH, searchButton, 15, SpringLayout.SOUTH, outputArea);
        layout.putConstraint(SpringLayout.SOUTH, searchButton, -5, SpringLayout.SOUTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, searchButton, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.EAST, searchButton, -5, SpringLayout.EAST, contentPane);
        contentPane.add(wordArea);
        layout.putConstraint(SpringLayout.WEST, wordArea, -350, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.EAST, wordArea, -5, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, wordArea, 5, SpringLayout.SOUTH, searchLabel);
        layout.putConstraint(SpringLayout.SOUTH, wordArea, 0, SpringLayout.SOUTH, outputArea);
        contentPane.add(wordLabel);
        layout.putConstraint(SpringLayout.WEST, wordLabel, 0, SpringLayout.WEST, wordArea);
        layout.putConstraint(SpringLayout.NORTH, wordLabel, -20, SpringLayout.NORTH, wordArea);

        searchButton.addActionListener(this);

        frame.pack();
        frame.setSize(850,600);
        frame.setVisible(true);
    }

    static void search() {
        String output = "";
        String wordSearch = searchAreaText.getText().toLowerCase().replaceAll("\n","");
        String[] words = wordAreaText.getText().toLowerCase().split("\n");
        String[] ogWords = wordAreaText.getText().split("\n");
        int charactersPerRow = Integer.parseInt(rowCharacterField.getText());

        for (int wordI=0; wordI < words.length; wordI++) {
            char firstLetter = words[wordI].charAt(0);
            for (int searchI=0; searchI < wordSearch.length(); searchI++) {
                if(wordSearch.charAt(searchI) == firstLetter) {
                    if ((searchI-1) != -1) {
                        if (wordSearch.charAt(searchI - 1) == words[wordI].charAt(1)) { //backwards word
                            boolean wordFound = true;
                            for (int i = 0; i < words[wordI].length(); i++) {
                                if (searchI - i > -1) {
                                    if (wordSearch.charAt(searchI - i) != words[wordI].charAt(i)) {
                                        wordFound = false;
                                        break;
                                    }
                                } else {
                                    wordFound = false;
                                    break;
                                }
                            }
                            if (wordFound) {
                                output = output + ogWords[wordI] + " found starting at Line " + (searchI / charactersPerRow + 1) + " Character " + (searchI % charactersPerRow + 1) + " Going Backwards.\n";
                                break;
                            }
                        }
                    }

                    if ((searchI+1) != (wordSearch.length())) {
                        if (wordSearch.charAt(searchI+1) == words[wordI].charAt(1)) { //forwards word
                            boolean wordFound = true;
                            for (int i=0; i < words[wordI].length(); i++) {
                                if (searchI+i < wordSearch.length()) {
                                    if (wordSearch.charAt(searchI+i) != words[wordI].charAt(i)) {
                                        wordFound = false;
                                        break;
                                    }
                                } else {
                                    wordFound = false;
                                    break;
                                }
                            }
                            if (wordFound) {
                                output = output + ogWords[wordI] + " found starting at Line" + (searchI / charactersPerRow + 1) + " Character " + (searchI % charactersPerRow + 1) + " Going Forwards.\n";
                                break;
                            }
                        }
                    }

                    if (searchI+charactersPerRow < wordSearch.length()) {
                        if (wordSearch.charAt(searchI+charactersPerRow) == words[wordI].charAt(1)) { //down word
                            boolean wordFound = true;
                            for (int i=0; i < words[wordI].length(); i++) {
                                if (searchI+(i*charactersPerRow) < wordSearch.length()) {
                                    if (wordSearch.charAt(searchI+(i*charactersPerRow)) != words[wordI].charAt(i)) {
                                        wordFound = false;
                                        break;
                                    }
                                } else {
                                    wordFound = false;
                                    break;
                                }
                            }
                            if (wordFound) {
                                output = output + ogWords[wordI] + " found starting at Line " + (searchI / charactersPerRow + 1) + " Character " + (searchI % charactersPerRow + 1) + " Going Downwards.\n";
                                break;
                            }
                        }
                    }

                    if (searchI-charactersPerRow > -1) {
                        if (wordSearch.charAt(searchI-charactersPerRow) == words[wordI].charAt(1)) { //up word
                            boolean wordFound = true;
                            for (int i=0; i < words[wordI].length(); i++) {
                                if (searchI-(i*charactersPerRow) > -1) {
                                    if (wordSearch.charAt(searchI-(i*charactersPerRow)) != words[wordI].charAt(i)) {
                                        wordFound = false;
                                        break;
                                    }
                                } else {
                                    wordFound = false;
                                    break;
                                }
                            }
                            if (wordFound) {
                                output = output + ogWords[wordI] + " found starting at Line " + (searchI / charactersPerRow + 1) + " Character " + (searchI % charactersPerRow + 1) + " Going Upwards.\n";
                                break;
                            }
                        }
                    }

                    if ((searchI-(charactersPerRow+1)) > -1 ) {
                        if (wordSearch.charAt(searchI-(charactersPerRow+1)) == words[wordI].charAt(1)) { //up diagonal left
                            boolean wordFound = true;
                            for (int i=0; i < words[wordI].length(); i++) {
                                if (searchI-(i*(charactersPerRow+1)) > -1) {
                                    if (wordSearch.charAt(searchI-(i*(charactersPerRow+1))) != words[wordI].charAt(i)) {
                                        wordFound = false;
                                        break;
                                    }
                                } else {
                                    wordFound = false;
                                    break;
                                }
                            }
                            if (wordFound) {
                                output = output + ogWords[wordI] + " found starting at Line " + (searchI / charactersPerRow + 1) + " Character " + (searchI % charactersPerRow + 1) + " Going Upwards and Left.\n";
                                break;
                            }
                        }
                    }

                    if ((searchI-(charactersPerRow-1)) > -1 ) {
                        if (wordSearch.charAt(searchI-(charactersPerRow-1)) == words[wordI].charAt(1)) { //up diagonal right
                            boolean wordFound = true;
                            for (int i=0; i < words[wordI].length(); i++) {
                                if (searchI-(i*(charactersPerRow-1)) > -1) {
                                    if (wordSearch.charAt(searchI - (i * (charactersPerRow - 1))) != words[wordI].charAt(i)) {
                                        wordFound = false;
                                        break;
                                    }
                                } else {
                                    wordFound = false;
                                    break;
                                }
                            }
                            if (wordFound) {
                                output = output + ogWords[wordI] + " found starting at Line " + (searchI / charactersPerRow + 1) + " Character " + (searchI % charactersPerRow + 1) + " Going Upwards and Right.\n";
                                break;
                            }
                        }
                    }

                    if ((searchI+(charactersPerRow-1) < wordSearch.length())) {
                        if (wordSearch.charAt(searchI+(charactersPerRow-1)) == words[wordI].charAt(1)) { //down diagonal left
                            boolean wordFound = true;
                            for (int i=0; i < words[wordI].length(); i++) {
                                if (searchI+(i*(charactersPerRow-1)) < wordSearch.length()) {
                                    if (wordSearch.charAt(searchI+(i*(charactersPerRow-1))) != words[wordI].charAt(i)) {
                                        wordFound = false;
                                        break;
                                    }
                                } else {
                                    wordFound = false;
                                    break;
                                }
                            }
                            if (wordFound) {
                                output = output + ogWords[wordI] + " found starting at Line " + (searchI / charactersPerRow + 1) + " Character " + (searchI % charactersPerRow + 1) + " Going Downwards and Left.\n";
                                break;
                            }
                        }
                    }

                    if (searchI+(charactersPerRow+1) < wordSearch.length()) {
                        if (wordSearch.charAt(searchI+(charactersPerRow+1)) == words[wordI].charAt(1)) { //down diagonal right
                            boolean wordFound = true;
                            for (int i=0; i < words[wordI].length(); i++) {
                                if (searchI+(i*(charactersPerRow+1)) < wordSearch.length()) {
                                    if (wordSearch.charAt(searchI+(i*(charactersPerRow+1))) != words[wordI].charAt(i)) {
                                        wordFound = false;
                                        break;
                                    }
                                } else {
                                    wordFound = false;
                                    break;
                                }
                            }
                            if (wordFound) {
                                output = output + ogWords[wordI] + " found starting at Line " + (searchI / charactersPerRow + 1) + " Character " + (searchI % charactersPerRow + 1) + " Going Downwards and Right.\n";
                                break;
                            }
                        }
                    }
                }
            }
            outputText.setText(output);
        }
    }
}