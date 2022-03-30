package com.harryWorld.navigationGPS.map.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Routes {
    @SerializedName("legs")
    @Expose
    private List<Legs> legs;

    public List<Legs> getLegs() {
        return legs;
    }

    public void setLegs(List<Legs> legs) {
        this.legs = legs;
    }

    public class Legs {
        @SerializedName("steps")
        @Expose
        private List<Steps> steps;

        public List<Steps> getSteps() {
            return steps;
        }

        public void setSteps(List<Steps> steps) {
            this.steps = steps;
        }


        public class Steps {
            @SerializedName("polyline")
            @Expose
            private PointsPolyline polyline;

            public PointsPolyline getPolyline() {
                return polyline;
            }

            public void setPolyline(PointsPolyline polyline) {
                this.polyline = polyline;
            }

            public class PointsPolyline {

                @SerializedName("points")
                @Expose
                private String points;

                public String getPoints() {
                    return points;
                }

                public void setPoints(String points) {
                    this.points = points;
                }
            }
        }
    }
}
