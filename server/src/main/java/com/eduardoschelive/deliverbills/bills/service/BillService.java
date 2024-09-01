package com.eduardoschelive.deliverbills.bills.service;

import com.eduardoschelive.deliverbills.bills.dto.BillDTO;
import com.eduardoschelive.deliverbills.bills.dto.UpsertBillDTO;
import com.eduardoschelive.deliverbills.bills.entity.Bill;
import com.eduardoschelive.deliverbills.bills.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class BillService {

    private final BillRepository billRepository;

    @Transactional
    public BillDTO create(UpsertBillDTO upsertBillDTO) {
        var newBill = upsertBillDTO.toEntity();

        var bill =  this.billRepository.save(newBill);

        log.info("Criado um uma nova conta com UUID {}", bill.getUuid());

        return BillDTO.fromEntity(bill);
    }

    @Transactional(readOnly = true)
    public Page<BillDTO> list(Specification<Bill> specification, Pageable pageable) {
        var bills =  this.billRepository.findAll(specification, pageable);
        return bills.map(BillDTO::fromEntity);
    }

    @Transactional
    public void delete(UUID uuid) {
        this.billRepository.removeByUuid(uuid);
        log.info("Conta com UUID {} deletada", uuid);
    }

    @Transactional
    public void update(UUID uuid, UpsertBillDTO upsertBillDTO) {
        var bill = upsertBillDTO.toEntity();
        bill.setUuid(uuid);
        this.billRepository.save(bill);
        log.info("Conta com UUID {} atualizada", uuid);
    }

}
