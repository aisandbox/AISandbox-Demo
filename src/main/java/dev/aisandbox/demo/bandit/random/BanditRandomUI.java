package dev.aisandbox.demo.bandit.random;

import dev.aisandbox.demo.NetworkInformation;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bandit/random")
public class BanditRandomUI {

  private final NetworkInformation network;

  @Autowired
  public BanditRandomUI(NetworkInformation network) {
    this.network = network;
  }

  @GetMapping("/index.html")
  public String index(Model model) {
    // work out server port and IP
    model.addAttribute("ip", network.getServerAddress());
    model.addAttribute("port", network.getServerPort());
    return "banditRandom";
  }
}
