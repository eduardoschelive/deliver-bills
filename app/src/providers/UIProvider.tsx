'use client'

import { NextUIProvider } from '@nextui-org/react'
import { useRouter } from 'next/navigation'
import { PropsWithChildren } from 'react'

export function UIProvider({ children }: PropsWithChildren) {
  const router = useRouter()
  return (
    <NextUIProvider navigate={router.push} locale="pt-BR">
      {children}
    </NextUIProvider>
  )
}
