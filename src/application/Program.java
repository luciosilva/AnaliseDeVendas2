package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entre com o caminho do arquivo: ");
		String path = sc.nextLine();
		final List<Sale> sales = new ArrayList<>();
		Set<String> sellerNames = null;
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			sellerNames = new HashSet<String>();
			
			String line = br.readLine();
			while(line != null) {
				String[] fields = line.split(",");
				sales.add(new Sale(Integer.parseInt(fields[0]),
						Integer.parseInt(fields[1]),
						fields[2],
						Integer.parseInt(fields[3]),
						Double.parseDouble(fields[4])
						));
				sellerNames.add(fields[2]);
				line = br.readLine();
			}
			
		} catch (IOException e) {
			System.out.println("Erro: " + path + " (O sistema não pode encontrar o arquivo especificado)");
			System.exit(1);
		}
		sc.close();
		
		System.out.println("\nTotal de vendas por vendedor:");
		sellerNames.forEach((sellerName) -> {
			Double sellerTotal = sales.stream()
					.filter(p -> p.getSeller().equals(sellerName))
					.map(p -> p.getTotal())
					.reduce(0.0, (x,y) -> x + y);
			System.out.println(sellerName + " - R$ " + String.format("%.2f", sellerTotal));
		});	
	}
}
