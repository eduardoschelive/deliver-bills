import { Bill } from '@/model'
import { Modal, ModalContent } from '@nextui-org/react'
import { PropsWithChildren } from 'react'
type BillModalProps = {
  isOpen: boolean
  bill?: Bill
  onClose?: () => void
}

export function BillModal({
  isOpen,
  onClose,
  children,
}: PropsWithChildren<BillModalProps>) {
  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <ModalContent>{children}</ModalContent>
    </Modal>
  )
}
