import java.util.Objects;

public class Request {
    private final String requestId;
    private final int floorNumber;
    private final Direction dir;
    private final RequestType type;

    public Request(String requestId, int floorNumber, Direction dir, RequestType type) {
        if (requestId == null || requestId.isBlank()) {
            throw new IllegalArgumentException("requestId is required");
        }
        if (floorNumber <= 0) {
            throw new IllegalArgumentException("floorNumber must be > 0");
        }
        if (dir == null) {
            throw new IllegalArgumentException("dir is required");
        }
        if (type == null) {
            throw new IllegalArgumentException("type is required");
        }
        this.requestId = requestId;
        this.floorNumber = floorNumber;
        this.dir = dir;
        this.type = type;
    }

    public String getRequestId() {
        return requestId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public Direction getDir() {
        return dir;
    }

    public RequestType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId='" + requestId + '\'' +
                ", floorNumber=" + floorNumber +
                ", dir=" + dir +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        Request request = (Request) o;
        return floorNumber == request.floorNumber
                && requestId.equals(request.requestId)
                && dir == request.dir
                && type == request.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, floorNumber, dir, type);
    }
}

