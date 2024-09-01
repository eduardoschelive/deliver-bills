import { PlusIcon } from '@/components/PlusIcon'
import { Button, ButtonProps } from '@nextui-org/react'

type AddBillButtonProps = ButtonProps

export const AddBillButton = ({ onClick, ...props }: AddBillButtonProps) => {
  return (
    <Button
      color="primary"
      onClick={onClick}
      endContent={<PlusIcon width={24} height={24} />}
      aria-label="Adicionar nova conta"
      {...props}
    >
      Adicionar nova conta
    </Button>
  )
}
