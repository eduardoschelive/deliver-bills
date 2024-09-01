import { ModalHeader } from '@nextui-org/react'
import { PropsWithChildren } from 'react'

export function BillModalHeader({ children }: PropsWithChildren) {
  return <ModalHeader>{children}</ModalHeader>
}
