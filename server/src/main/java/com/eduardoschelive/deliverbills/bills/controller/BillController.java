package com.eduardoschelive.deliverbills.bills.controller;

import com.eduardoschelive.deliverbills.bills.dto.BillDTO;
import com.eduardoschelive.deliverbills.bills.dto.UpsertBillDTO;
import com.eduardoschelive.deliverbills.bills.entity.Bill;
import com.eduardoschelive.deliverbills.bills.service.BillService;
import com.eduardoschelive.deliverbills.bills.utils.Endpoint;
import com.eduardoschelive.deliverbills.common.dto.PagedModel;
import com.eduardoschelive.deliverbills.filter.annotation.FilterEndpoint;
import com.eduardoschelive.deliverbills.filter.utils.FilterUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(Endpoint.Bills.BASE_PATH)
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PostMapping
    public ResponseEntity<Void> createBill(@Valid @RequestBody UpsertBillDTO upsertBillDTO) {
        var bill = this.billService.create(upsertBillDTO);

        return ResponseEntity.created(URI.create(Endpoint.Bills.BASE_PATH + "/" + bill.uuid())).build();
    }

    @GetMapping
    @FilterEndpoint(Bill.class)
    public ResponseEntity<PagedModel<BillDTO>> listBills(Specification<Bill> specification, Pageable pageable) {
        var bills = this.billService.list(specification, pageable);
        var pagedModel = new PagedModel<>(bills);
        return FilterUtils.buildResponseEntity(pagedModel);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteBill(@PathVariable UUID uuid) {
        this.billService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Void> updateBill(@PathVariable UUID uuid, @Valid @RequestBody UpsertBillDTO upsertBillDTO) {
        this.billService.update(uuid, upsertBillDTO);
        return ResponseEntity.noContent().build();
    }

}
