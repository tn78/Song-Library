//Tejas Nimkar, ALEXANDER NOCCIOLO
package SongLibrarypckg;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private Button addButton;

    @FXML
    private TextField albumText;

    @FXML
    private TextField artistText;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private ListView<Song> listView;

    @FXML
    private TextField nameText;

    @FXML
    private TextField yearText;

//    public boolean checkExists(String n, String a) {
//        ObservableList<Song> temp = listView.getItems();
//        for (Song x : temp) {
//            if (x.nam.equals(n) && x.art.equals(a)) {
//                return true;
//            }
//        }
//        return false;
//    }
    public int findIndex(Song check){
        int index = 0;
        ObservableList<Song> obsList = listView.getItems();
        for(Song x: obsList){
            String currentSong = x.toString();
            if(currentSong.compareToIgnoreCase(check.toString()) < 0){
                index++;
            }else{
                break;
            }
        }
        return index;
    }
    public boolean checkExists(String n, String a, int index) {
        int count = 0;
        ObservableList<Song> temp = listView.getItems();
        for(Song x: temp) {
            if(x.nam.compareToIgnoreCase(n) == 0 && x.art.compareToIgnoreCase(a) == 0) {             
                if(index == -1){
                    return true;
                }else{
                    //count++;
                    if(count != index){
                        return true;
                    }
                }
            }
            count++;
        }
        return false;
    }
    @FXML
    void addSong(MouseEvent event) throws IOException {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = confirm.showAndWait();
        if(result.get() == ButtonType.CANCEL) {
            return;
        }
        String name;
        String artist;
        String album;
        int year;
        Song addedSong;
        FileWriter fw = new FileWriter("Songs.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        if(!yearText.getText().isBlank())
        {
            if(Integer.parseInt(yearText.getText()) < 0)
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Year is negative");
                error.showAndWait();
                bw.close();
                fw.close();
                return;
            }
        }   
        if (nameText.getText().isBlank() || artistText.getText().isBlank() || checkExists(nameText.getText(), artistText.getText(),-1)) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Either Song/Artist field is empty,Song/Artist combo exists already, or Year is negative.");
            err.showAndWait();
        } else { if (albumText.getText().isBlank() && yearText.getText().isBlank()) {
       //     listView.getItems().add(new Song(nameText.getText(), artistText.getText()));
            name = nameText.getText();
            artist = artistText.getText();
            name = name.trim();
            artist = artist.trim();
            addedSong = new Song(name, artist);
            bw.write("\n" + nameText.getText() + "|" + artistText.getText());
        } else if (yearText.getText().isBlank()) {
        //    listView.getItems().add(new Song(nameText.getText(), artistText.getText(), albumText.getText()));
            name = nameText.getText();
            artist = artistText.getText();
            album = albumText.getText();
            name = name.trim();
            artist = artist.trim();
            album = album.trim();
            addedSong = new Song(name, artist, album);
            bw.write("\n" + nameText.getText() + "|" + artistText.getText() + "|-1|" + albumText.getText());
        } else if (albumText.getText().isBlank()) {
    //        listView.getItems().add(new Song(nameText.getText(), artistText.getText(), Integer.parseInt(yearText.getText())));
            name = nameText.getText();
            artist = artistText.getText();
            year = Integer.parseInt(yearText.getText());
            name = name.trim();
            artist = artist.trim();
            addedSong = new Song(name, artist, year);
            bw.write("\n" + nameText.getText() + "|" + artistText.getText() + "|" + yearText.getText());
        } else {
          //  listView.getItems().add(new Song(nameText.getText(), artistText.getText(), Integer.parseInt(yearText.getText()),albumText.getText()));
            name = nameText.getText();
            artist = artistText.getText();
            year = Integer.parseInt(yearText.getText());
            album = albumText.getText();
            name = name.trim();
            artist = artist.trim();
            album = album.trim();
            addedSong = new Song(name, artist, year, album);
            bw.write("\n" + nameText.getText() + "|" + artistText.getText() + "|" + yearText.getText() + "|"+ albumText.getText());
        }
        if(listView.getItems().isEmpty()){
            listView.getItems().add(addedSong);
            listView.getSelectionModel().select(0);
        }else{
            int index = findIndex(addedSong);
            listView.getItems().add(index, addedSong);
            listView.getSelectionModel().select(index);
            nameText.setText(listView.getSelectionModel().getSelectedItem().nam);
            artistText.setText(listView.getSelectionModel().getSelectedItem().art);
            if(listView.getSelectionModel().getSelectedItem().alb!=null) {
                albumText.setText(listView.getSelectionModel().getSelectedItem().alb);
            }   
            if(listView.getSelectionModel().getSelectedItem().yr!=-1) {
                yearText.setText(String.valueOf(listView.getSelectionModel().getSelectedItem().yr));
            }
        }
        }
        bw.close();
        fw.close();
        
    }
    


    @FXML
    void deleteSong(MouseEvent event) throws NumberFormatException, IOException {
//        if (!listView.getItems().isEmpty()) {
//            Alert err = new Alert(Alert.AlertType.CONFIRMATION);
//            err.setContentText(
//                    "Please confirm that you want to delete selected song:");
//            //System.out.println(err.getResult().getButtonData().getTypeCode());
//            err.showAndWait();
////            if(err.getButtonTypes().)) {
////                return;
////            }else {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = confirm.showAndWait();
        if(result.get() == ButtonType.CANCEL) {
            return;
        }
            int selected = listView.getSelectionModel().getSelectedIndex();
            File temp = new File("temp.txt");
      //      System.out.println(temp.createNewFile());
//            if (temp.createNewFile()) {
//                System.out.println(temp.getPath());
//            }
            FileReader fr = new FileReader("Songs.txt");
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter("temp.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitup = line.split("\\|");
                if (splitup[0].equals(nameText.getText()) && splitup[1].equals(artistText.getText())) {
                    continue;
                } else {
                    String finwrite = "";
                    for (int i = 0; i < splitup.length - 1; i++) {
                        finwrite += splitup[i] + "|";
                    }
                    finwrite += splitup[splitup.length - 1];
                    bw.newLine();
                    bw.write(finwrite);
                }
            }
            bw.close();
            fw.close();
            br.close();
            fr.close();
            temp.renameTo(new File("Songs.txt"));
            listView.getItems().remove(selected);
            nameText.clear();
            artistText.clear();
            yearText.clear();
            albumText.clear();
        
            if(!listView.getItems().isEmpty() && listView.getItems().size() >= selected){   
                listView.getSelectionModel().select(selected);
                nameText.setText(listView.getSelectionModel().getSelectedItem().nam);
                artistText.setText(listView.getSelectionModel().getSelectedItem().art);
                if(listView.getSelectionModel().getSelectedItem().alb!=null) {
                    albumText.setText(listView.getSelectionModel().getSelectedItem().alb);
                }
                if(listView.getSelectionModel().getSelectedItem().yr!=-1) {
                    yearText.setText(String.valueOf(listView.getSelectionModel().getSelectedItem().yr));
                }
            }
            else if(listView.getItems().size() < selected && listView.getItems().size() > 0){
                listView.getSelectionModel().select(selected - 1);
                nameText.setText(listView.getSelectionModel().getSelectedItem().nam);
                artistText.setText(listView.getSelectionModel().getSelectedItem().art);
                if(listView.getSelectionModel().getSelectedItem().alb!=null) {
                    albumText.setText(listView.getSelectionModel().getSelectedItem().alb);
                }
                if(listView.getSelectionModel().getSelectedItem().yr!=-1) {
                    yearText.setText(String.valueOf(listView.getSelectionModel().getSelectedItem().yr));
                }
            }
            
        }
  //  }

    @FXML
    void editSong(MouseEvent event) throws IOException {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = confirm.showAndWait();
            if(result.get() == ButtonType.CANCEL) {
                return;
            }
        
            if(nameText.getText().isBlank() || artistText.getText().isBlank() || checkExists(nameText.getText(), artistText.getText(), listView.getSelectionModel().getSelectedIndex())) {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setContentText("Either Song/Artist field is empty,Song/Artist combo exists already, or Year is negative.");
                err.showAndWait();
            }else {
                
                FileReader fr = new FileReader("Songs.txt");
                BufferedReader br = new BufferedReader(fr);
                FileWriter fw = new FileWriter("temp.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                String line;
                File temp = new File("temp.txt");
                
                String name;
                String artist;
                String album;
                int year;
                Song addedSong;
                if(!yearText.getText().isBlank()){
                    if(Integer.parseInt(yearText.getText()) < 0){
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.showAndWait();
                        return;
                    }
                }            
                    if(albumText.getText().isBlank() && yearText.getText().isBlank()) {
                        name = nameText.getText();
                        artist = artistText.getText();
                        name = name.trim();
                        artist = artist.trim();
                        addedSong = new Song(name, artist);
                        
                        while ((line = br.readLine()) != null) {
                            String[] splitup = line.split("\\|");
                            if (!(splitup[0].equals(listView.getSelectionModel().getSelectedItem().nam) && splitup[1].equals(listView.getSelectionModel().getSelectedItem().art))) {
                                bw.newLine();
                                bw.write(line);
                            } else {
                                bw.newLine();
                                bw.write(nameText.getText()+"|"+artistText.getText());
                            }
                        }
                        bw.close();
                        fw.close();
                        br.close();
                        fr.close();
                        temp.renameTo(new File("Songs.txt"));
                        
                    }else if(yearText.getText().isBlank()) {
                        name = nameText.getText();
                        artist = artistText.getText();
                        album = albumText.getText();
                        name = name.trim();
                        artist = artist.trim();
                        album = album.trim();
                        addedSong = new Song(name, artist, album);
                        
                        while ((line = br.readLine()) != null) {
                            String[] splitup = line.split("\\|");
                            if (!(splitup[0].equals(listView.getSelectionModel().getSelectedItem().nam) && splitup[1].equals(listView.getSelectionModel().getSelectedItem().art))) {
                                bw.newLine();
                                bw.write(line);
                            } else {
                                bw.newLine();
                                bw.write(nameText.getText()+"|"+artistText.getText()+"|-1|"+albumText.getText());
                            }
                        }
                        bw.close();
                        fw.close();
                        br.close();
                        fr.close();
                        temp.renameTo(new File("Songs.txt"));
                        
                    }else if(albumText.getText().isBlank()) {
                        name = nameText.getText();
                        artist = artistText.getText();
                        year = Integer.parseInt(yearText.getText());
                        name = name.trim();
                        artist = artist.trim();
                        addedSong = new Song(name, artist, year);
                        
                        while ((line = br.readLine()) != null) {
                            String[] splitup = line.split("\\|");
                            if (!(splitup[0].equals(listView.getSelectionModel().getSelectedItem().nam) && splitup[1].equals(listView.getSelectionModel().getSelectedItem().art))) {
                                bw.newLine();
                                bw.write(line);
                            } else {
                                bw.newLine();
                                bw.write(nameText.getText()+"|"+artistText.getText()+"|"+yearText.getText());
                            }
                        }
                        bw.close();
                        fw.close();
                        br.close();
                        fr.close();
                        temp.renameTo(new File("Songs.txt"));
                        
                    }else {
                        name = nameText.getText();
                        artist = artistText.getText();
                        year = Integer.parseInt(yearText.getText());
                        album = albumText.getText();
                        name = name.trim();
                        artist = artist.trim();
                        album = album.trim();
                        addedSong = new Song(name, artist, year, album);
                        
                        while ((line = br.readLine()) != null) {
                            String[] splitup = line.split("\\|");
                            if (!(splitup[0].equals(listView.getSelectionModel().getSelectedItem().nam) && splitup[1].equals(listView.getSelectionModel().getSelectedItem().art))) {
                                bw.newLine();
                                bw.write(line);
                            } else {
                                bw.newLine();
                                bw.write(nameText.getText()+"|"+artistText.getText()+"|"+yearText.getText()+"|"+albumText.getText());
                            }
                        }
                        bw.close();
                        fw.close();
                        br.close();
                        fr.close();
                        temp.renameTo(new File("Songs.txt"));
                        
                    }
                    listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
                    int index = findIndex(addedSong);
                    listView.getItems().add(index, addedSong);
                    listView.getSelectionModel().select(index);
                    nameText.setText(listView.getSelectionModel().getSelectedItem().nam);
                    artistText.setText(listView.getSelectionModel().getSelectedItem().art);
                    if(listView.getSelectionModel().getSelectedItem().alb!=null) {
                        albumText.setText(listView.getSelectionModel().getSelectedItem().alb);
                    }   
                    if(listView.getSelectionModel().getSelectedItem().yr!=-1) {
                        yearText.setText(String.valueOf(listView.getSelectionModel().getSelectedItem().yr));
                    }
                }                        
        
//        if (nameText.getText().isBlank() || artistText.getText().isBlank()
//                || checkExists(nameText.getText(), artistText.getText())) {
//            Alert err = new Alert(Alert.AlertType.ERROR);
//            err.showAndWait();
//        } else {
//            listView.getSelectionModel().getSelectedItem().nam = nameText.getText();
//            listView.getSelectionModel().getSelectedItem().yr = Integer.parseInt(yearText.getText());
//            listView.getSelectionModel().getSelectedItem().alb = albumText.getText();
//            listView.getSelectionModel().getSelectedItem().art = artistText.getText();
//            nameText.clear();
//            artistText.clear();
//            yearText.clear();
//            albumText.clear();
//        }
    }

    @FXML
    void viewSong(MouseEvent event) {
        if(!listView.getItems().isEmpty()) {
            nameText.clear();
            artistText.clear();
            yearText.clear();
            albumText.clear();
            nameText.setText(listView.getSelectionModel().getSelectedItem().nam);
            artistText.setText(listView.getSelectionModel().getSelectedItem().art);
            if (listView.getSelectionModel().getSelectedItem().alb != null) {
                albumText.setText(listView.getSelectionModel().getSelectedItem().alb);
            }
            if (listView.getSelectionModel().getSelectedItem().yr != -1) {
                yearText.setText(String.valueOf(listView.getSelectionModel().getSelectedItem().yr));
            }
        }
    }

    public void start(Stage primaryStage) throws IOException {
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        FileReader fr = new FileReader("Songs.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        Song addedSong = null;
        while ((line = br.readLine()) != null) {
            String[] splitup = line.split("\\|");
            if (splitup.length == 2) {
             //   listView.getItems().add(new Song(splitup[0], splitup[1]));// name, artist
                addedSong = new Song(splitup[0], splitup[1]);
            }
            if (splitup.length == 3) {
                if (splitup[2].equals("deleted")) {
                    continue;
                }
             //   listView.getItems().add(new Song(splitup[0], splitup[1], Integer.parseInt(splitup[2])));// name,artist,year
                addedSong = new Song(splitup[0], splitup[1], Integer.parseInt(splitup[2]));
            }
            if (splitup.length == 4) {
                if (splitup[3].equals("deleted")) {
                    continue;
                }
                if (Integer.parseInt(splitup[2]) == -1) {
            //        listView.getItems().add(new Song(splitup[0], splitup[1], splitup[3]));// name,artist,album
                    addedSong = new Song(splitup[0], splitup[1], splitup[3]);
                } else {
             //       listView.getItems().add(new Song(splitup[0], splitup[1], Integer.parseInt(splitup[2]), splitup[3]));// name,
                                                                                                                        // artist,year,album
                    addedSong = new Song(splitup[0], splitup[1], Integer.parseInt(splitup[2]), splitup[3]);
                }
            }
            if(listView.getItems().isEmpty() && addedSong!= null){
                listView.getItems().add(addedSong);
                listView.getSelectionModel().select(0);
            }else if(addedSong != null){
                int index = findIndex(addedSong);
                listView.getItems().add(index, addedSong);
                listView.getSelectionModel().select(index);
                nameText.setText(listView.getSelectionModel().getSelectedItem().nam);
                artistText.setText(listView.getSelectionModel().getSelectedItem().art);
                if(listView.getSelectionModel().getSelectedItem().alb!=null) {
                    albumText.setText(listView.getSelectionModel().getSelectedItem().alb);
                }   
                if(listView.getSelectionModel().getSelectedItem().yr!=-1) {
                    yearText.setText(String.valueOf(listView.getSelectionModel().getSelectedItem().yr));
                }
            }            
        }
        br.close();
        fr.close();
        if(!listView.getItems().isEmpty()) {
            listView.getSelectionModel().select(0);
            nameText.setText(listView.getItems().get(0).nam);
            artistText.setText(listView.getItems().get(0).art);
            if (listView.getItems().get(0).alb != null) {
                albumText.setText(listView.getItems().get(0).alb);
            }
            if (listView.getItems().get(0).yr != -1) {
                yearText.setText(String.valueOf(listView.getItems().get(0).yr));
            }
        }
    }
}
