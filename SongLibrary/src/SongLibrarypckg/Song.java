//Tejas Nimkar, ALEXANDER NOCCIOLO
package SongLibrarypckg;
public class Song {
	public String nam;
	public String art;
	public int yr;
	public String alb;
	
	public Song(String name, String artist) {
		this.nam = name;
		this.art = artist;
		this.yr=-1;
	}
	public Song(String name, String artist, int year) {
		this.nam = name;
		this.art = artist;
		this.yr = year;
	}
	public Song(String name, String artist, String album) {
		this.nam = name;
		this.art = artist;
		this.alb = album;
		this.yr=-1;
	}
	public Song(String name, String artist, int year, String album) {
		this.nam = name;
		this.art = artist;
		this.alb = album;
		this.yr = year;
	}
	public void setName(String n) {
		this.nam = n;
	}
	public void setYear(int n) {
		this.yr = n;
	}
	public void setArtist(String n) {
		this.art = n;
	}
	public void setAlbum(String n) {
		this.alb = n;
	}
	public String toString() {
	    return this.nam + "|" + this.art;
	}
	public String toStringTotal() {
	    return this.nam+"|"+this.art+"|"+this.alb+"|"+this.yr;
	}
}
