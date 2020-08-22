package dev.aisandbox.demo.bandit.greedy;

import dev.aisandbox.demo.bandit.api.BanditRequest;
import dev.aisandbox.demo.bandit.api.BanditResponse;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BanditGreedyAPI {
  private Random rand = new Random(System.currentTimeMillis());

  BanditSession currentSession = null;

  @PostMapping(
      path = "/ai/bandit/greedy",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public BanditResponse getRandomDefaultResponse(
      @PathVariable("e") double epsilon, @RequestBody BanditRequest req) {
    return getRandomResponse(0.1, req);
  }

  @PostMapping(
      path = "/ai/bandit/greedy/{e}",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public BanditResponse getRandomResponse(
      @PathVariable("e") double epsilon, @RequestBody BanditRequest req) {
    // record history
    if ((req.getHistory() != null)
        && (currentSession != null)
        && (currentSession.getSessionID().equals(req.getSessionID()))) {
      currentSession.recordReward(req.getHistory().getChosenBandit(), req.getHistory().getReward());
    }
    // check for a new session
    if ((currentSession == null) || (!currentSession.getSessionID().equals(req.getSessionID()))) {
      currentSession = new BanditSession(req.getSessionID(), req.getBanditCount());
    }
    log.info("choose from {} bandits, pull {} of {}",req.getBanditCount(),req.getPull(),req.getPullCount());
    int arm;
    // if there are unpulled bandits, pull them
    if (!currentSession.getUnpulled().isEmpty()) {
      log.info("Picking unpulled arm");
      arm = currentSession.getUnpulled().iterator().next();
    } else if (rand.nextDouble() >= epsilon) {
      // pick the best bandit
      arm = currentSession.getBestBandit();
      log.info("Picking best arm {} from averages {}", arm, currentSession.getAverageReward());
    } else {
      // pick a random bandit if rand(0..1) < epsilon
      arm = rand.nextInt(req.getBanditCount());
      log.info("Picking random arm {}", arm);
    }
    // pull !
    BanditResponse response = new BanditResponse();
    response.setArm(arm);
    return response;
  }
}
