package nc.isi.fragaria_reflection.services;

import org.reflections.scanners.Scanner;

public interface ScannerProvider {

	Scanner[] provide();

}
