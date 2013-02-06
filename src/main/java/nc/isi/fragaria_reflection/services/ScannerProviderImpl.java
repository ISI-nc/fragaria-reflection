package nc.isi.fragaria_reflection.services;

import java.util.Collection;

import org.reflections.scanners.Scanner;

public class ScannerProviderImpl implements ScannerProvider {
	private final Collection<Scanner> scanners;

	public ScannerProviderImpl(Collection<Scanner> scanners) {
		this.scanners = scanners;
	}

	@Override
	public Scanner[] provide() {
		return scanners.toArray(new Scanner[scanners.size()]);
	}

}
