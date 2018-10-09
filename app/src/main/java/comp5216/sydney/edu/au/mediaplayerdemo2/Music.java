package comp5216.sydney.edu.au.mediaplayerdemo2;

public class Music {
    private String name;
    private String artist;
    //private String song;

    public Music(String name, String artist) {
        this.name = name;
        this.artist = artist;
       // this.song = song;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }


}
