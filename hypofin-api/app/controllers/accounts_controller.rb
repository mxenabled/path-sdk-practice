# frozen_string_literal: true

class AccountsController < ApplicationController
  before_action :validate_header

  def index
    render json: [
      {
        id: 'acct-123',
        desc: 'Checking Account',
        t: 'CHK',
        bal: (rand * 10_000).round(2)
      },
      {
        id: 'acct-456',
          desc: 'Savings Account',
          t: 'SAV',
          bal: (rand * 10_000).round(2)
      },
      {
        id: 'acct-456',
          desc: 'Auto Loan',
          t: 'LOAN',
          bal: (rand * 30_000).round(2)
      }
    ]
  end

  def get
    render json: {
      id: 'acct-123',
      desc: 'Checking Account',
      t: 'CHK',
      bal: (rand * 10_000).round(2)
    }
  end

  private

  def validate_header
    return render status: :unauthorized unless request.headers['token'].present?

    true
  end
end
