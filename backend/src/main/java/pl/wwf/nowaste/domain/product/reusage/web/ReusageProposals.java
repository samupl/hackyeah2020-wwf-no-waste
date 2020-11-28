package pl.wwf.nowaste.domain.product.reusage.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReusageProposals {

    private List<ReusageDetails> forProduct;
    private List<ReusageDetails> proposals;

}
