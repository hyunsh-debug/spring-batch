package kr.co.batch.sample.dvo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Data
public class WriteDvo {
	private int readTestId;
	private String name;
	private String createTime;
	private String modifyTime;
}
