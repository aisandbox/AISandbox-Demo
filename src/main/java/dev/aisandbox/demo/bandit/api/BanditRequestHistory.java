package dev.aisandbox.demo.bandit.api;

import lombok.Data;

/** BanditRequestHistory */
@Data
public class BanditRequestHistory {
  private String sessionID;
  private int pull;
  private double reward;
}
