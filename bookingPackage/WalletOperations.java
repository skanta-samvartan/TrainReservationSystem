package bookingPackage;

import java.io.*;

public interface WalletOperations extends Serializable {
  static final long serialVersionUID = 1L;

  boolean updateWallet(double fare);

  double checkBalance();

  void displayWalletStatus();
}