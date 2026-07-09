

    public abstract class Person {
        private String name;
        private String location;
        private String phoneNumber;

        public Person(String name, String location, String phoneNumber) {
            this.name = name;
            this.location = location;
            this.phoneNumber = phoneNumber;
        }

        // Getters & Setters (Encapsulation)
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }

        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

        // Abstract method (Polymorphism)
        public abstract String getRole();

        @Override
        public String toString() {
            return "[" + getRole() + "] " + name + " | " + location;
        }
    }

