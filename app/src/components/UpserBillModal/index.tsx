'use client'
import { Bill, billSchema } from '@/model'
import { zodResolver } from '@hookform/resolvers/zod'
import { Button, Input } from '@nextui-org/react'
import { FormProvider, useForm } from 'react-hook-form'
import {
  BillModal,
  BillModalBody,
  BillModalFooter,
  BillModalHeader,
} from '../BillModal'
import { ControlledDateInput } from '../ControlledDateInput'

type UpsertBillModal = {
  bill?: Bill
  isOpen: boolean
  isPending?: boolean
  onClose?: () => void
  onSubmit: (bill: Bill) => Promise<void>
}

const defaultValues = {
  name: '',
  originalValue: 0,
  dueDate: new Date(),
  paymentDate: new Date(),
}

export function UpsertBillModal({
  isOpen,
  onClose,
  onSubmit,
  isPending,
  bill,
}: UpsertBillModal) {
  const values = {
    ...defaultValues,
    ...bill,
  }

  const methods = useForm<Bill>({
    resolver: zodResolver(billSchema),
    values: values,
  })

  const {
    handleSubmit,
    register,
    reset,
    formState: { errors },
  } = methods

  const handleClose = () => {
    reset()
    onClose?.()
  }

  const submitActions = async (bill: Bill) => {
    await onSubmit(bill)
    handleClose()
  }

  return (
    <BillModal isOpen={isOpen} onClose={handleClose}>
      <BillModalHeader>Insira as informações da conta</BillModalHeader>
      <FormProvider {...methods}>
        <form onSubmit={handleSubmit(submitActions)}>
          <BillModalBody>
            <Input
              autoFocus
              label="Nome"
              placeholder="Insira o nome da conta"
              type="text"
              variant="bordered"
              errorMessage={errors.name?.message}
              isInvalid={!!errors.name}
              isRequired
              {...register('name')}
            />
            <Input
              label="Valor original"
              placeholder="Insira o valor original da conta"
              type="number"
              datatype="currency"
              variant="bordered"
              errorMessage={errors.originalValue?.message}
              isInvalid={!!errors.originalValue}
              isRequired
              startContent={
                <div className="pointer-events-none flex items-center">
                  <span className="text-default-400 text-small">R$</span>
                </div>
              }
              {...register('originalValue', { valueAsNumber: true })}
            />
            <ControlledDateInput<Bill>
              name="dueDate"
              label="Data de vencimento"
              variant="bordered"
              isRequired
              hideTimeZone
              showMonthAndYearPickers
            />
            <ControlledDateInput<Bill>
              name="paymentDate"
              label="Data de pagamento"
              variant="bordered"
              isRequired
              hideTimeZone
              showMonthAndYearPickers
            />
          </BillModalBody>
          <BillModalFooter>
            <Button type="button" color="danger" onClick={handleClose}>
              Cancelar
            </Button>
            <Button type="submit" color="primary" isLoading={isPending}>
              Salvar
            </Button>
          </BillModalFooter>
        </form>
      </FormProvider>
    </BillModal>
  )
}
