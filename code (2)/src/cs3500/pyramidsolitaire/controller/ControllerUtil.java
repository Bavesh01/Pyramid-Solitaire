package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;


class ControllerUtil {

  protected static PyramidSolitaireModel model;
  protected static Appendable out;


  public static Map<String, Function<Scanner, ICommand>> getCommands(
      PyramidSolitaireModel model, Appendable out) {
    Map<String, Function<Scanner, ICommand>> knownCommands =
        new HashMap<String, Function<Scanner, ICommand>>();
    knownCommands.putIfAbsent("rm1",  scanner -> {
      return new RemoveOneCommand(model); } );
    knownCommands.putIfAbsent("rm2",  scanner -> {
      return new RemoveTwoCommand(model); } );
    knownCommands.putIfAbsent("rmwd", scanner -> {
      return new RemoveDrawCommand(model); } );
    knownCommands.putIfAbsent("dd",   scanner -> {
      return new DiscardCommand(model); } );
    return knownCommands;
  }
}
