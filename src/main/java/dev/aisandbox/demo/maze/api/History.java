/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.aisandbox.demo.maze.api;

import lombok.Data;

/**
 * <p>History class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
@Data
public class History {

    private Position lastPosition;
    private String action;
    private double reward;
    private Position newPosition;

}
