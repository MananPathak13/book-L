package ca.sheridancollge.beans;

import lombok.Data;

@Data
public class Review {

	private Long id;
	private Long BookId;
	private String text;
}
