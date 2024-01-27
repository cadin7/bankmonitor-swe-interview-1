package bankmonitor.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {

    public static final String REFERENCE_KEY = "reference";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_at")
    private LocalDateTime timestamp;

    @Column(name = "data")
    private String data;

    public Transaction(String jsonData) {
        this.timestamp = LocalDateTime.now();
        this.data = jsonData;
    }

    public Integer getAmount() {
        JSONObject jsonData = new JSONObject(this.data);
        if (jsonData.has("amount")) {
            return jsonData.getInt("amount");
        } else {
            return -1;
        }
    }

    public String getReference() {
        JSONObject jsonData = new JSONObject(this.data);
        if (jsonData.has(REFERENCE_KEY)) {
            return jsonData.getString(REFERENCE_KEY);
        } else {
            return "";
        }
    }
}