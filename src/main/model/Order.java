package main.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    private Integer id;
    private User agent;
    private User buyer;
    private Date date;
    private String buyingItemName;
    private String buyingComment;
    private String soldComment;
    private Boolean done;
    private Boolean archived;
    private Boolean canceled;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_id")
    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "buyer_id", nullable = false)
    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", columnDefinition = "CURRENT_TIMESTAMP", insertable = false, updatable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "buying_item_name", length = 100, nullable = false)
    public String getBuyingItemName() {
        return buyingItemName;
    }

    public void setBuyingItemName(String buyingItemName) {
        this.buyingItemName = buyingItemName;
    }

    @Column(name = "buying_comment", length = 500, nullable = false)
    public String getBuyingComment() {
        return buyingComment;
    }

    public void setBuyingComment(String buyingComment) {
        this.buyingComment = buyingComment;
    }

    @Column(name = "sold_comment", length = 500)
    public String getSoldComment() {
        return soldComment;
    }

    public void setSoldComment(String soldComment) {
        this.soldComment = soldComment;
    }

    @Column(name = "is_done")
    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Column(name = "is_archived")
    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    @Column(name = "is_canceled")
    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean closed) {
        this.canceled = closed;
    }
}
