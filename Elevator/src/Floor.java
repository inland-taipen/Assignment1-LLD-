public class Floor {
    private final int floorId;

    public Floor(int floorId) {
        if (floorId <= 0) {
            throw new IllegalArgumentException("floorId must be > 0");
        }
        this.floorId = floorId;
    }

    public int getFloorId() {
        return floorId;
    }
}

