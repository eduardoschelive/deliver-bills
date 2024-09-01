import { z } from 'zod'

export const billSchema = z.object({
  uuid: z.string().uuid().optional(),
  name: z
    .string({
      message: 'O nome da conta é obrigatório',
    })
    .min(3, {
      message: 'O nome da conta deve ter no mínimo 3 caracteres',
    }),
  originalValue: z
    .number({
      message: 'O valor original da conta é obrigatório',
    })
    .positive({
      message: 'O valor original da conta deve ser maior que zero',
    }),
  dueDate: z.date({
    message: 'A data de vencimento da conta é obrigatória',
  }),
  paymentDate: z.date({
    message: 'A data de pagamento da conta é obrigatória',
  }),
})

export type Bill = z.infer<typeof billSchema>
