package com.example.demo0713.lammbda.demo3;

import com.google.common.base.MoreObjects;

class Apple {
        int id;
        String color;
        public Apple(int id, String color) {
            this.id = id;
            this.color = color;
        }
        public int getId() {
            return id;
        }
        public String getColor() {
            return color;
        }
        @Override
        public String toString() {
            return MoreObjects.toStringHelper(Apple.class).add("id", id).add("color", color).toString();
        }
    }