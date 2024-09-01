import { DetailedBill } from '@/model'
import { useDisclosure } from '@nextui-org/react'
import { useState } from 'react'

export function useUpsertBillDisclousure() {
  const { onOpen, onClose, isOpen } = useDisclosure()
  const [detailedBill, setDetailedBill] = useState<DetailedBill | undefined>()

  const openUpsetBillModal = (bill: DetailedBill | undefined) => {
    setDetailedBill(bill)
    onOpen()
  }

  return {
    isUpsetBillModalOpen: isOpen,
    closeUpsetBillModal: onClose,
    openUpsetBillModal,
    detailedBill,
  }
}
