package fantfootball.datamodel;

public class Team {

        public static final Team EMPTY_TEAM = new Team("NA", "", -1);
    
        private int id;
        
        private String name;

        private String link;

        public Team(String name, String link, int id) {
            this.name = name;
            this.link = link;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getLink() {
            return link;
        }

        public String toString() {
            return id + "," + name + "," + link;
        }

        public int getId() {
            return id;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + id;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Team other = (Team) obj;
            if (id != other.id)
                return false;
            return true;
        }
        

   
}
