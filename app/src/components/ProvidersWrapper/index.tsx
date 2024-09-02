'use client'
import { QueryProvider, UIProvider } from '@/providers'
import { ThemeProvider } from 'next-themes'
import { PropsWithChildren } from 'react'

export function ProvidersWrapper({ children }: PropsWithChildren) {
  return (
    <QueryProvider>
      <UIProvider>
        <ThemeProvider attribute="class" enableSystem={false}>
          {children}
        </ThemeProvider>
      </UIProvider>
    </QueryProvider>
  )
}
